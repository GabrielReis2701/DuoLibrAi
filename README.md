# DuoLibrAI

## Descrição do Projeto
Este projeto é um aplicativo desenvolvido como trabalho de conclusão de curso. Ele é projetado para detectar sinais da Língua Brasileira de Sinais (Libras), ajudando a promover a comunicação e a inclusão.

## Funcionalidades
- **Detecção de Sinais**: O aplicativo pode detectar uma variedade de sinais em Libras, utilizando 3 modelos de classificação de imagens, sendo eles:
    1. Modelo de classificação de Letras estaticas do alfabeto em Libras, feita por [Lucas Lacerda](https://github.com/lucaaslb/cnn-libras).
    2. Modelo de classificação de numeros e palavras em Libras, feitos por [Andréia Araújo](https://github.com/Andreia-oliv/Projeto_Libras).
- **Alerta diario**: Através do Firebase o aplicativo pode enviar notificações push, programadas.
- **Troca de plano de fundo**: Pode ser configurado até 3 planos de fundo para o aplicativo.

## Como Usar
1. Faça o download do aplicativo.
2. Abra o aplicativo e conceda as permissões necessárias.
3. Como o aplicativo não possui um segmentador para o reconhecimento de mãos, é necessario tirar as fotos dos gestos de Libras em um ambiente bem luminado e sem qualquer objeto de fundo.
