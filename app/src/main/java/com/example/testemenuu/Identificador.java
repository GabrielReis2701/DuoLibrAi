package com.example.testemenuu;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.InterpreterApi;
import org.tensorflow.lite.InterpreterFactory;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Map;

public class Identificador {
    private Context context;
    final String ASSOCIATED_AXIS_LABELSL = "labels.txt";
    final String ASSOCIATED_AXIS_LABELSN = "labels_numero.txt";
    List<String> associatedAxisLabels = null;
    String letraComMaiorPrecisao="";

    public Identificador(Context context) {
        this.context = context;
    }

    public String classificarImagem(Bitmap image,int tipo){
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeOp(64, 64, ResizeOp.ResizeMethod.BILINEAR))
                        .add(new NormalizeOp(0.0f,255.0f))
                        .build();

        // Create a TensorImage object. This creates the tensor of the corresponding
        // tensor type (uint8 in this case) that the TensorFlow Lite interpreter needs.
        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);

        // Analysis code for every frame
        // Preprocess the image
        tensorImage.load(image);
        tensorImage = imageProcessor.process(tensorImage);

        // Create a container for the result and specify that this is a quantized model.
// Hence, the 'DataType' is defined as UINT8 (8-bit unsigned integer)
        TensorBuffer probabilityBuffer;
        if(tipo==1){
             probabilityBuffer =
                    TensorBuffer.createFixedSize(new int[]{1, 21}, DataType.FLOAT32);
        }else{
            probabilityBuffer =
                    TensorBuffer.createFixedSize(new int[]{1, 10}, DataType.FLOAT32);
        }
        
        // Initialise the model
        InterpreterApi tflite = null;
        try {
            MappedByteBuffer tfliteModel = null;
            if(tipo==1){
                tfliteModel = FileUtil.loadMappedFile(context, "modeloClassificadorSinais.tflite");
            }else{
                tfliteModel = FileUtil.loadMappedFile(context, "model_classificador_numeros.tflite");
            }
            tflite = new InterpreterFactory().create(
                    tfliteModel, new InterpreterApi.Options());
        } catch (IOException e) {
            Log.e("tfliteSupport", "Error reading model", e);
        }

// Running inference
        if (null != tflite) {
            tflite.run(tensorImage.getBuffer(), probabilityBuffer.getBuffer());
        }


        try {
            if(tipo==1){
                associatedAxisLabels = FileUtil.loadLabels(context, ASSOCIATED_AXIS_LABELSL);
            }else{
                associatedAxisLabels = FileUtil.loadLabels(context, ASSOCIATED_AXIS_LABELSN);
            }
        } catch (IOException e) {
            Log.e("tfliteSupport", "Error reading label file", e);
        }
        // Post-processor which dequantize the result
        TensorProcessor probabilityProcessor =
                new TensorProcessor.Builder().add(new NormalizeOp(0, 255)).build();

        if (null != associatedAxisLabels) {
            // Map of labels and their corresponding probability
            TensorLabel labels = new TensorLabel(associatedAxisLabels,
                    probabilityProcessor.process(probabilityBuffer));

            // Create a map to access the result based on label
            Map<String, Float> floatMap = labels.getMapWithFloatValue();
            Map.Entry<String, Float> maxEntry = null;
            for (Map.Entry<String, Float> entry : floatMap.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            letraComMaiorPrecisao = maxEntry.getKey();
            System.out.println(letraComMaiorPrecisao);
        }
        return letraComMaiorPrecisao;
    }
}
