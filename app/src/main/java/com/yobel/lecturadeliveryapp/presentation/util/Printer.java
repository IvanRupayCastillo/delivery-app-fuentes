package com.yobel.lecturadeliveryapp.presentation.util;

import android.os.Looper;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;

public class Printer {


    public static void sendZplOverBluetoothDemo1(final String theBtMacAddress) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Establece una conexión insegura para la dirección MAC Bluetooth proporcionada.
                    Connection thePrinterConn = new BluetoothConnection(theBtMacAddress);

                    // Inicializa el Looper.
                    Looper.prepare();

                    // Abre la conexión física.
                    thePrinterConn.open();

                    // Código ZPL para imprimir "OK".
                    String zplData = "^XA\n" +
                            "\n" +
                            "^FX Top section with logo, name and address.\n" +
                            "^CF0,60\n" +
                            "^FO50,50^GB100,100,100^FS\n" +
                            "^FO75,75^FR^GB100,100,100^FS\n" +
                            "^FO93,93^GB40,40,40^FS\n" +
                            "^FO220,50^FDIntershipping, Inc.^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,115^FD1000 Shipping Lane^FS\n" +
                            "^FO220,155^FDShelbyville TN 38102^FS\n" +
                            "^FO220,195^FDUnited States (USA)^FS\n" +
                            "^FO50,250^GB700,3,3^FS\n" +
                            "\n" +
                            "^FX Second section with recipient address and permit information.\n" +
                            "^CFA,30\n" +
                            "^FO50,300^FDJohn Doe^FS\n" +
                            "^FO50,340^FD100 Main Street^FS\n" +
                            "^FO50,380^FDSpringfield TN 39021^FS\n" +
                            "^FO50,420^FDUnited States (USA)^FS\n" +
                            "^CFA,15\n" +
                            "^FO600,300^GB150,150,3^FS\n" +
                            "^FO638,340^FDPermit^FS\n" +
                            "^FO638,390^FD123456^FS\n" +
                            "^FO50,500^GB700,3,3^FS\n" +
                            "\n" +
                            "^FX Third section with bar code.\n" +
                            "^BY5,2,270\n" +
                            "^FO100,550^BC^FD12345678^FS\n" +
                            "\n" +
                            "^FX Fourth section (the two boxes on the bottom).\n" +
                            "^FO50,900^GB700,250,3^FS\n" +
                            "^FO400,900^GB3,250,3^FS\n" +
                            "^CF0,40\n" +
                            "^FO100,960^FDCtr. X34B-1^FS\n" +
                            "^FO100,1010^FDREF1 F00B47^FS\n" +
                            "^FO100,1060^FDREF2 BL4H8^FS\n" +
                            "^CF0,190\n" +
                            "^FO470,955^FDCA^FS\n" +
                            "\n" +
                            "^XZ";                     // Fin del trabajo de impresión

                    // Envía los datos a la impresora como un array de bytes.
                    thePrinterConn.write(zplData.getBytes());

                    // Asegúrate de que los datos se envíen antes de cerrar la conexión.
                    Thread.sleep(5000);

                    // Cierra la conexión para liberar recursos.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Maneja el error de comunicación aquí.
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public static void sendZplOverBluetoothDemo2(final String theBtMacAddress) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Establece una conexión insegura para la dirección MAC Bluetooth proporcionada.
                    Connection thePrinterConn = new BluetoothConnection(theBtMacAddress);

                    // Inicializa el Looper.
                    Looper.prepare();

                    // Abre la conexión física.
                    thePrinterConn.open();

                    // Código ZPL para imprimir "OK".
                    String zplData = "^XA\n" +
                            "\n" +
                            "^FX Top section with logo, name and address.\n" +
                            "^CF0,40\n" +
                            "^FO220,35^FD973^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,75^FDSantiago^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,115^FDSantiago 38102^FS\n" +
                            "^CF0,40\n" +
                            "^FO220,155^FDRuta 22201^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,195^FD0^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,235^FDMMXQP160493345R^FS\n" +
                            "\n" +
                            "^XZ";                     // Fin del trabajo de impresión

                    // Envía los datos a la impresora como un array de bytes.
                    thePrinterConn.write(zplData.getBytes());

                    // Asegúrate de que los datos se envíen antes de cerrar la conexión.
                    Thread.sleep(1000);

                    // Cierra la conexión para liberar recursos.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Maneja el error de comunicación aquí.
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public static void sendZplOverBluetooth(
            final String theBtMacAddress,
            final String sequence,
            final String zona1,
            final String zona2,
            final String route,
            final String upload,
            final String track
    ) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Establece una conexión insegura para la dirección MAC Bluetooth proporcionada.
                    Connection thePrinterConn = new BluetoothConnection(theBtMacAddress);

                    // Inicializa el Looper.
                    Looper.prepare();

                    // Abre la conexión física.
                    thePrinterConn.open();

                    // Código ZPL para imprimir "OK".
                    String zplData = "^XA\n" +
                            "\n" +
                            "^FX Top section with logo, name and address.\n" +
                            "^CF0,40\n" +
                            "^FO220,35^FD" + sequence + "^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,75^FD" + zona1 + "^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,115^FD" + zona2 +"^FS\n" +
                            "^CF0,40\n" +
                            "^FO220,155^FD" + route + "^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,195^FD" + upload + "^FS\n" +
                            "^CF0,30\n" +
                            "^FO220,235^FD" + track + "^FS\n" +
                            "\n" +
                            "^XZ";                     // Fin del trabajo de impresión

                    // Envía los datos a la impresora como un array de bytes.
                    thePrinterConn.write(zplData.getBytes());

                    // Asegúrate de que los datos se envíen antes de cerrar la conexión.
                    Thread.sleep(1000);

                    // Cierra la conexión para liberar recursos.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Maneja el error de comunicación aquí.
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
