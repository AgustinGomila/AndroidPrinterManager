# Administrador de Impresión para Android (Ejemplo)

Este es un ejemplo sencillo de un administrador de impresión para aplicaciones Android, escrito en Kotlin. El proyecto
muestra cómo implementar un sistema flexible para imprimir en diferentes tipos de impresoras.

## Características

- Soporte para impresoras de red (TCP/IP)
- Soporte para impresoras Bluetooth
- Sistema de fábrica para seleccionar el tipo de impresora según la configuración
- Manejo de errores con mensajes informativos
- Multi-hilo para no bloquear la interfaz de usuario

## Cómo funciona

El sistema permite seleccionar entre dos tipos de impresoras mediante configuración:

1. **Impresora de red**: Conecta a una impresora mediante su dirección IP y puerto
2. **Impresora Bluetooth**: Conecta a una impresora Bluetooth emparejada

La selección se realiza a través de variables de configuración (`settingsVm.useNetPrinter` y `settingsVm.useBtPrinter`).

## Uso básico

1. Configura el tipo de impresora que deseas usar en tus ajustes
2. Para impresoras de red, establece la IP y puerto
3. Para impresoras Bluetooth, empareja el dispositivo y configura su dirección MAC
4. Usa el factory para crear el objeto impresora:

```kotlin
val printer = Printer.PrinterFactory.createPrinter(
    activity = this,
    onEvent = { event ->
        // Manejar eventos (mensajes, errores)
    }
)

// Luego imprime
printer?.printLabel("Texto a imprimir", 1) { success ->
    // Callback cuando termine de imprimir
}
```

## Permisos requeridos

- Para impresión Bluetooth, el proyecto maneja los permisos necesarios (BLUETOOTH_CONNECT)
- El sistema solicita permisos automáticamente cuando son necesarios

Este es un ejemplo básico que puedes adaptar y expandir según tus necesidades específicas de impresión en tu aplicación
Android.