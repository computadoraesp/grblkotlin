cmake_minimum_required(VERSION 3.10.2)

# Nombre de tu proyecto (puedes cambiarlo)
project("garbleAndroid")

# Agrega los archivos C++ que deseas compilar
add_library(
        native-lib
        SHARED
        src/main/cpp/native-lib.cpp
)

# Encontrar la biblioteca 'log' que es útil para imprimir logs en Android
find_library(
        log-lib
        log
)

# Enlaza las bibliotecas necesarias (en este caso, la biblioteca 'log')
target_link_libraries(
        native-lib
        ${log-lib}
)
