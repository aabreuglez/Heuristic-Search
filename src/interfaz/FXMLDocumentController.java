/*
 *PROYECTO DE:
 *-ADRIAN ABREU GONZALEZ
 *-ELIANA ABDEL MAJID HASSAN
 *-ANDRÉS CIDONCHA CARBALLO
 *PARA INTELIGENCIA ARTIFICIAL EN EL CURSO 14-15
 */

package interfaz;

import interfaz.ayuda.Coordenadas;
import interfaz.ayuda.Datos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import logica.Partida;


public class FXMLDocumentController implements Initializable {
    /*------------------------------------------
     DECLARACION DE VARIABLES DE LA INTERFAZ
     ------------------------------------------*/

    @FXML
    private Label label, ini_lAvisoColumnas, ini_lAvisoFilas, ini_lAvisocarga, ini_lavisodestino, ini_lAvisoRobot, man_lAvisonum, man_lAvisocoord, man_lAvisoCasillaOcupada, play_NoSolucion;

    @FXML
    private Button ini_bGenerar, man_bGenerar, matrix_bPlay;

    @FXML
    private TextField ini_tFfilas, ini_tFcolumnas, ini_cObstaculos, ini_tfDestino, ini_tfRobot;

    @FXML
    private ChoiceBox ini_cbTemas;

    @FXML
    private RadioButton ini_bAutomatico, ini_bManual;

    @FXML
    private Tab tabManual, tabMatrix;

    @FXML
    private TilePane man_tpPreview, matrix_tpDibujo;

    @FXML
    private CheckBox man_cbPreview;

    public static final ObservableList temas = FXCollections.observableArrayList("Hospital", "Chatarra", "Zoologico");

    @FXML
    private TextField tfNumObs, tfCoord;
    @FXML
    private StackPane spPane;
    @FXML
    private ListView spLista;

    public static final ObservableList coordenadas = FXCollections.observableArrayList();
    public static Partida juego;

    @FXML
    AnchorPane matrix_panelMain;
    /*------------------------------------------
     FIN DE DECLARACIÓN DE VARIABLES DE INTERFAZ
     ------------------------------------------*/

    /*---------------------------------------
     FUNCIONES DE AYUDA
     -----------------------------------------*/
    //Validador para el parseo a int y el numero positivo
    public static boolean isIntegerPositive(String s) {
        try {
            Integer a = Integer.parseInt(s);
            return a > 0;
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
    }

    /*---------------------------------------
     FIN DE FUNCIONES DE AYUDA
     -----------------------------------------*/
    /* -------------------------------------------------
     PESTAÑA DE INICIO
     -------------------------------------------------*/
    @FXML
    private boolean recAncho() { //Esta función recogera los valores de los textos de inicio
        boolean pivote1 = false, pivote2 = false, pivote3 = false, pivote4 = false; //Pivotes que nos dirán los valores que hemos recogido
        int filas = 0, columnas = 0;
        if (!ini_tFfilas.getText().isEmpty()) { //Si no está vacio
            if (isIntegerPositive(ini_tFfilas.getText())) { //Comprobamos que dentro hay una letra
                filas = Integer.parseInt(ini_tFfilas.getText());//Devuelve valor como una string y la parsea a Int
                Datos.tabla.put(Datos.FILAS, filas); //Lo guardamos en la tabla de dispersión
                ini_lAvisoFilas.setText(""); //Limpiamos la label de aviso 
                pivote1 = true; //Priver pivote on
            } else { //Si esta vacio printamos error
                ini_lAvisoFilas.setText("¿Las filas son un numero positivo?");
            }
        } else { //Si está vacío printamos error
            ini_lAvisoFilas.setText("¿Las filas son un numero positivo?");
        }
        //Repetimos para columnas lo de las filas
        if (!ini_tFcolumnas.getText().isEmpty()) {
            if (isIntegerPositive(ini_tFcolumnas.getText())) {
                columnas = Integer.parseInt(ini_tFcolumnas.getText());
                Datos.tabla.put(Datos.COLUMNAS, columnas);
                ini_lAvisoColumnas.setText("");
                pivote2 = true; //pivote dos on
            } else { //Printamos error
                ini_lAvisoColumnas.setText("¿Las columnas son un numero positivo?");
            }
        } else {
            ini_lAvisoColumnas.setText("¿Las columnas son un numero positivo?");
        }
        //Comprobamos que opción está marcada
        if (ini_bManual.isSelected()) { //Si está en modo manual
            boolean split1 = false, split2 = false; //Booleanos para coordenadas
            int destinoFila = 0, destinoColumna = 0; //Enteros de destino
            if (!ini_tfDestino.getText().isEmpty()) { //Si el recuadro no está vacío

                String[] coordenadasSplitted = ini_tfDestino.getText().split(","); //Creamos una string partida
                try { //Hacemos un try
                    if (isIntegerPositive(coordenadasSplitted[0])) { //Comprobamos que el primer elemento es un numero
                        destinoFila = Integer.parseInt(coordenadasSplitted[0]); //Lo guardamos
                        split1 = true; //Ponemos el pivote a uno
                    } else { //Si no es numero lanzamos error
                        ini_lavisodestino.setText("Revisa el formato de destino");
                    }
                    //Repetimos para el segundo elemento
                    if (isIntegerPositive(coordenadasSplitted[1])) {
                        destinoColumna = Integer.parseInt(coordenadasSplitted[1]);
                        split2 = true;
                    } else {
                        ini_lavisodestino.setText("Revisa el formato de destino");
                    }
                    //Si ha fallado el intento, manejamos la excepción
                } catch (IndexOutOfBoundsException e) {
                    ini_lavisodestino.setText("Revisa el formato de  destino");
                }

            } else { //Si resulta que el cuadro está vacío, lanzamos la error
                ini_lavisodestino.setText("Revisa el formato de  destino");

            }
            //Repetimos lo mismo para la casilla de robot
            boolean split3 = false, split4 = false;
            int robotFila = 0, robotColumna = 0;

            if (!ini_tfRobot.getText().isEmpty()) {

                String[] coordenadasSplitted = ini_tfRobot.getText().split(",");
                try {
                    if (isIntegerPositive(coordenadasSplitted[0])) {
                        robotFila = Integer.parseInt(coordenadasSplitted[0]);
                        split3 = true;

                    } else {
                        ini_lAvisoRobot.setText("Revisa el formato de  robot");
                    }
                    if (isIntegerPositive(coordenadasSplitted[1])) {
                        robotColumna = Integer.parseInt(coordenadasSplitted[1]);
                        split4 = true;

                    } else {
                        ini_lAvisoRobot.setText("Revisa el formato de robot");
                    }
                } catch (IndexOutOfBoundsException e) {
                    ini_lAvisoRobot.setText("Revisa el formato de  robot");
                }

            } else {
                ini_lAvisoRobot.setText("Revisa el formato de  robot");

            }
            //Tras haber comprobado todos los valores, hacemos la comprobación
            if (split1 && split2 && split3 && split4) { //Si hemos recogido todos los valors
                if ((robotFila <= filas) && (robotColumna <= columnas) && (destinoFila <= filas) && (destinoColumna <= columnas)) { //Comprobamos que esté dentro de los límites
                    if ((robotFila != destinoFila) || (robotColumna != destinoColumna)) { //Y que al menos la fila o la columna sean distintos
                        //Insertamos los valores en la tabla hash
                        Datos.tabla.put(Datos.FROBOT, robotFila);
                        Datos.tabla.put(Datos.CROBOT, robotColumna);
                        Datos.tabla.put(Datos.CDESTINO, destinoColumna);
                        Datos.tabla.put(Datos.FDESTINO, destinoFila);
                        //Confirmamos la siguiente insersión
                        pivote3 = true;
                    } else {
                        //En caso contrario printamos para ambos que el valor debe ser diferentes
                        ini_lavisodestino.setText("Los valores deben ser distintos");
                        ini_lAvisoRobot.setText("Los valores deben ser distintos");
                    }
                } else { //O printamos que los valores están fuera de los limites
                    ini_lavisodestino.setText("Los valores deben estar dentro de los limites");
                    ini_lAvisoRobot.setText("Los valores deben estar dentro de los limites");
                }

            }
        } //Si no está en modo manual
        else {
            //No necesitamos comprobar el modo de destino, asi que lo damos por veradero
            pivote3 = true;
        }
        return pivote1 && pivote2 && pivote3; //Retornamos el haber cogido satisfoctariamente los 3 valores
    }

    @FXML
    private void cambioDemodo() { //Esta función se encarga de habilitar y deshabilitar las partes correspondientes al modo manual y automático
        ToggleGroup group = new ToggleGroup(); //Creamos un grupo para que solo se pueda escoger uno de los botones, de esta forma
        ini_bAutomatico.setToggleGroup(group); // si habilitamos manual se deshabilitara automático
        ini_bManual.setToggleGroup(group);

        if (ini_bAutomatico.isSelected()) { //Cuando el modo es automático
            Datos.tabla.put(Datos.MODO, 1); //Guardamos el valor en la tabla de dispersión
            ini_cObstaculos.setDisable(false); //Habilitamos la carga de obstaculos
            ini_bGenerar.setDisable(false); //Habilitamos el botón generar
            tabManual.setDisable(true); //Deshabilitamos las partes
            ini_tfRobot.setDisable(true);//referente al modo manual
            ini_tfDestino.setDisable(true);
        } else { //Lo mismo a la inversa, al ser un grupo, si automático no está seleccionado
            Datos.tabla.put(Datos.MODO, 0);  //entonces solo puede estar seleccionado el modo manual
            ini_cObstaculos.setDisable(true); //Deshabilitamos las partes
            ini_bGenerar.setDisable(true); //relativas al modo automático
            tabManual.setDisable(false); //Y habilitamos las partes relativas
            man_bGenerar.setDisable(false); //al modo manual
            ini_tfRobot.setDisable(false);
            ini_tfDestino.setDisable(false);
        }
    }

    /* -------------------------------------------------
     Pestaña manual
     -------------------------------------------------*/
    @FXML
    private void recCoords() { //Esta función se encarga de extraer los valores de coordenadas de las casillas 
        int coordFila = 0, coordColumna = 0; //Ponemos unos valores por defecto
        Coordenadas pruebacoordenadas; //Creamos un objeto de pruebas
        boolean pivote1 = false, pivote2 = false; //Pivotes para la comprobación
        String[] coordenadasSplitted = tfCoord.getText().split(","); //Separamos la string que recogemos del cam`po de texto
        try { //Intentamos extraer su valor 
            if (isIntegerPositive(coordenadasSplitted[0])) { //Si es un numero entero positivo
                coordFila = Integer.parseInt(coordenadasSplitted[0]); //Guardamos su valor
                pivote1 = true; //Consideramos un exito
            } else {
                man_lAvisocoord.setText("Revisa el formato de coordenadas"); //Si no, mensaje de aviso
            }
            if (isIntegerPositive(coordenadasSplitted[1])) {
                coordColumna = Integer.parseInt(coordenadasSplitted[1]);
                pivote2 = true;
            } else {
                man_lAvisocoord.setText("Revisa el formato de  coordenadas");
            }
        } catch (IndexOutOfBoundsException e) {
            man_lAvisocoord.setText("Revisa el formato de coordenadas");
        }
        //out.println(Arrays.toString(coordenadasSplitted));
        boolean repetido = false; //Booleano para comprobar si el valor es repeito
        boolean novalid = false; //O si al casilla está ocupada por la meta o el robot
        if (recAncho()) { //Recogemos el ancho, el alto y las casillas de meta y robot
            if (pivote1 && pivote2) { //Comprobamos que se han extraido los valorees con exito
                pruebacoordenadas = new Coordenadas(coordFila, coordColumna); //Creamos el objeto de pruebas coordenadas
                man_lAvisocoord.setText(""); //Limpiamos los avisos
                    /*Ahora hacemos las siguientes comprobaciones:
                 1-Que el valor esté dentro de los límites, ya es mayor que 0 gracias a la función de ayuda.
                 2-Que el valor no coincida con la meta (siendo la casilla o la columna diferentes.
                 3-Que el vlaor no coincida con el robot.
                 */
                if ((pruebacoordenadas.getX() <= Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString())) && (pruebacoordenadas.getY() <= Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString()))) {
                    if ((pruebacoordenadas.getX() != (Integer.parseInt(Datos.tabla.get(Datos.FDESTINO).toString()))) || ((pruebacoordenadas.getY()) != (Integer.parseInt(Datos.tabla.get(Datos.CDESTINO).toString())))) {
                        if ((pruebacoordenadas.getX() != (Integer.parseInt(Datos.tabla.get(Datos.FROBOT).toString()))) || ((pruebacoordenadas.getY() != (Integer.parseInt(Datos.tabla.get(Datos.CROBOT).toString()))))) {
                            //Si todo eso se cumple, revisamos la cantidad de obstaculos que hemos marcado
                            if (isIntegerPositive(tfNumObs.getText())) {
                                if (coordenadas.size() < Integer.parseInt(tfNumObs.getText())) { //Si la cantidad de objetos que hemos metido es menor que el numero de objetos podemos meterlo
                                    if (coordenadas.size() != 0) { //Pero antes miramos si está en la lista
                                        for (Object it : coordenadas) { //Iterando sobre ella
                                            if ((((Coordenadas) it).getX() == pruebacoordenadas.getX()) && (((Coordenadas) it).getY() == pruebacoordenadas.getY())) {

                                                repetido = true; //Activamos el booleano
                                                man_lAvisocoord.setText("Valor repetido"); //Y printamos el error
                                            }
                                        }

                                    }
                                    if ((!repetido)) { //Si todo está bien
                                        coordenadas.add(pruebacoordenadas);//Añadimos el valor
                                        final ListView llista = new ListView(coordenadas); //Creamos una lista view
                                        llista.setPrefSize(200, 550); //Ajustams los parametros
                                        llista.setEditable(false);
                                        spLista.setItems(coordenadas); //Cargamos la list view en el panel apilable
                                        if (man_cbPreview.isSelected()) {

                                            //PASAMOS A GENERAR LA PREVIEW
                                            int a, b;
                                            //Recogemos los valores de la tabla hash
                                            b = Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString());
                                            a = Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString());

                                            //Ponemos las preferencia del tilepanel
                                            man_tpPreview.setPrefRows(a);
                                            man_tpPreview.setPrefColumns(b);
                                            man_tpPreview.setPrefWidth(17 * a);//setMaxWidth(17 * a);
                                            man_tpPreview.setMaxHeight(5 * b);
                                            //Si ya había una preview marcada, limpiamos el contenido
                                            for (int i = 0; i < (b * a); i++) {
                                                man_tpPreview.getChildren().clear();
                                            }
                                            boolean trovat = false; //Este booleano es para cambiar la preferencia, la impresión es simple
                                            //Se imprime en fila, por ello, hay que imprimir ancho*alto veces
                                            for (int i = 1; i <= (b * a); i++) {
                                                trovat = false; //Ponemos el trobat a false
                                                for (Object it : coordenadas) { //Iteramos sobre la lista de coordenadas para saber si hay un obstaculo
                                                    if ((((((Coordenadas) it).getX() - 1) * b) + (((Coordenadas) it).getY())) == i) {
                                                        man_tpPreview.getChildren().add(new Label("[+]")); //Si lo hay, imprimos un carácter distinto 
                                                        trovat = true; //Y ponemos el bool a true
                                                    }
                                                }
                                                if (!trovat) { //Si no hemos impreso en esta iteración
                                                    man_tpPreview.getChildren().add(new Label("[-]")); //Imprimimos un hueco normal
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //Aquí ponemos todos los mensajes de error
                        } else {
                            man_lAvisocoord.setText("Esa casilla está ocupada por el robot");
                        }
                    } else {
                        man_lAvisocoord.setText("Esa casilla está ocupada por la meta");
                    }
                } else {
                    man_lAvisocoord.setText("Revisa que las coordenadas esten dentro de los limites");
                }
            }

            //Si en cambio no vamos a hacer impresión, hace
        }
    }

    @FXML
    private void eliminarCoords() { //Esta función elimina la coordenada de la lista cuyos valores estén escritos en las caisllas
        //Mismo praton que ara añadir una coordenada
        int coordFila = 0, coordColumna = 0;
        Coordenadas pruebacoordenadas;
        boolean pivote1 = false, pivote2 = false;
        String[] coordenadasSplitted = tfCoord.getText().split(",");
        try {
            if (isIntegerPositive(coordenadasSplitted[0])) {
                coordFila = Integer.parseInt(coordenadasSplitted[0]);
                pivote1 = true;
            } else {
                man_lAvisocoord.setText("Revisa el formato de coordenadas");
            }
            if (isIntegerPositive(coordenadasSplitted[1])) {
                coordColumna = Integer.parseInt(coordenadasSplitted[1]);
                pivote2 = true;
            } else {
                man_lAvisocoord.setText("Revisa el formato de  coordenadas");
            }
        } catch (IndexOutOfBoundsException e) {
            man_lAvisocoord.setText("Revisa el formato de  coordenadas");
        }
        if (pivote1 && pivote2) {
            pruebacoordenadas = new Coordenadas(coordFila, coordColumna);
            man_lAvisocoord.setText("");
            if (coordenadas.size() > 0) {
                for (Object it : coordenadas) {
                    if ((((Coordenadas) it).getX() == coordFila) && (((Coordenadas) it).getY()) == coordColumna) {
                        coordenadas.remove(it); //Esta es la diferencia, si encontramos el valor, lo removemos de la lista
                        break; //Terminamos el bucle

                    }
                }
            }
            //Rehacemos la lista y las previews
            final ListView llista = new ListView(coordenadas);
            llista.setPrefSize(200, 550);
            llista.setEditable(false);
            spLista.setItems(coordenadas);
            if (man_cbPreview.isSelected()) {

                //PASAMOS A GENERAR LA PREVIEW
                int a, b;
                //Recogemos los valores de la tabla hash
                b = Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString());
                a = Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString());

                //Ponemos las preferencia del tilepanel
                man_tpPreview.setPrefRows(a);
                man_tpPreview.setPrefColumns(b);
                man_tpPreview.setPrefWidth(17 * a);//setMaxWidth(17 * a);
                man_tpPreview.setMaxHeight(5 * b);
                //Si ya había una preview marcada, limpiamos el contenido
                for (int i = 0; i < (b * a); i++) {
                    man_tpPreview.getChildren().clear();
                }
                boolean trovat = false; //Este booleano es para cambiar la preferencia, la impresión es simple
                //Se imprime en fila, por ello, hay que imprimir ancho*alto veces
                for (int i = 1; i <= (b * a); i++) {
                    trovat = false; //Ponemos el trobat a false
                    for (Object it : coordenadas) { //Iteramos sobre la lista de coordenadas para saber si hay un obstaculo
                        if ((((((Coordenadas) it).getX() - 1) * b) + (((Coordenadas) it).getY())) == i) {
                            man_tpPreview.getChildren().add(new Label("[+]")); //Si lo hay, imprimos un carácter distinto 
                            trovat = true; //Y ponemos el bool a true
                        }
                    }
                    if (!trovat) { //Si no hemos impreso en esta iteración
                        man_tpPreview.getChildren().add(new Label("[-]")); //Imprimimos un hueco normal
                    }
                }
            }
        }

    }
    /* -------------------------------------------------
     Fin pestaña manual
     -------------------------------------------------*/
    /* -------------------------------------------------
     Pestaña Matriz
     -------------------------------------------------*/

    @FXML
    private void generarceldas() {
        //Esta funcion pinta el dibujo
        //Variables para controlar el TILEPANEL
        int a, b;
        b = Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString());
        a = Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString());
        //Variable que determina el ancho de las imagenes
        int ancho = 20;
        //Definimos la cantidad de tilepanels que hay
        matrix_tpDibujo.setPrefRows(a);
        matrix_tpDibujo.setPrefColumns(b);
        //Dependiendo del tamaño de la matriz variamos el ancho
        if (a <= 10 && b <= 10) {
            ancho = 40;
        } else if (a < 20 && b <= 20) {
            ancho = 30;
        }
        matrix_tpDibujo.setPrefWidth(ancho * a);//setMaxWidth(ancho * a * ancho);
        matrix_tpDibujo.setPrefHeight(ancho * b);//setMaxHeight(ancho * b * ancho);
        //Limpiamos el panel
        for (int i = 0; i < (b * a); i++) {
            matrix_tpDibujo.getChildren().clear();
        }
        /*Declaradas las imagenes, para usar una URI getClass().getResource("img").toExternalForm()*/
        Image libre = new Image(getClass().getResource("empty" + Integer.toString(ancho) + ".png").toExternalForm());
        Image camino = new Image(getClass().getResource("camino" + Integer.toString(ancho) + ".png").toExternalForm());

        //Segun el tema variamos las imagenes de robot
        Image robot = new Image(getClass().getResource("robot" + Integer.toString(ancho) + ".png").toExternalForm());
        if ("Hospital".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            robot = new Image(getClass().getResource("Isaac" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        if ("Zoologico".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            robot = new Image(getClass().getResource("Rojo" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        //Segun el tema variamos las imagenes de obstaculo
        Image obstaculo = new Image(getClass().getResource("obstacle" + Integer.toString(ancho) + ".png").toExternalForm());
        if ("Hospital".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            obstaculo = new Image(getClass().getResource("Metalblock" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        if ("Zoologico".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            obstaculo = new Image(getClass().getResource("PokeabuH" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        //Segun el tema variamos las imagenes de meta
        Image meta = new Image(getClass().getResource("meta" + Integer.toString(ancho) + ".png").toExternalForm());
        if ("Hospital".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            meta = new Image(getClass().getResource("Itemroomdoor" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        if ("Zoologico".equals(Datos.tabla.get(Datos.TEMA).toString())) {
            meta = new Image(getClass().getResource("pokeball" + Integer.toString(ancho) + ".png").toExternalForm());
        }
        boolean trovat = false;

        /*Recorremos la matriz atributo*/
        for (int i = 0; i < Integer.parseInt(Datos.tabla.get(Datos.FILAS).toString()); i++) {
            for (int j = 0; j < Integer.parseInt(Datos.tabla.get(Datos.COLUMNAS).toString()); j++) {
                //- libre, + obstaculo, * robot
                //Imrpimos las imgview segun el codigo
                if (juego.getobjeto(i, j) == '-') {
                    matrix_tpDibujo.getChildren().add(new ImageView(libre));
                } else if (juego.getobjeto(i, j) == '+') {
                    matrix_tpDibujo.getChildren().add(new ImageView(obstaculo));
                } else if (juego.getobjeto(i, j) == '*') {
                    matrix_tpDibujo.getChildren().add(new ImageView(robot));
                } else if (juego.getobjeto(i, j) == ',') {
                    matrix_tpDibujo.getChildren().add(new ImageView(camino));
                } else if (juego.getobjeto(i, j) == '.') {
                    matrix_tpDibujo.getChildren().add(new ImageView(meta));
                }

            }
        }
    }

    @FXML
    private void generar() {
        /*Recogemos todos los valores para la tabla de dispersión*/
        play_NoSolucion.setText(""); //Limpiamos la etiqueta del fondo
        if (recAncho()) { //Comprobamos que se han introducido los valores
            int carga;
            boolean test = false;
            if (ini_bAutomatico.isSelected()) { //Si está en modo automático recogemos la carga de obstaculos
                if (!ini_cObstaculos.getText().isEmpty()) {
                    if (isIntegerPositive(ini_cObstaculos.getText())) {
                        carga = Integer.parseInt(ini_cObstaculos.getText());
                        Datos.tabla.put(Datos.CARGA, carga);
                        test = true;
                        ini_lAvisocarga.setText("");
                    } else {
                        ini_lAvisocarga.setText("¿La carga de obstaculos es un numero positivo?");
                    }
                } else {
                    ini_lAvisocarga.setText("¿La carga de obstaculos es un numero positivo?");
                }
            } 
            else { //Si está en modo manual recogemos los valores de la pestaña manual
                if (!tfNumObs.getText().isEmpty()) {
                    test = true;
                    if (isIntegerPositive(tfNumObs.getText())) {
                        int numobs = Integer.parseInt(tfNumObs.getText());
                        Datos.tabla.put(Datos.NUM_OBS, numobs);
                        Datos.tabla.put(Datos.LISTA, coordenadas);
                    }
                }
            }


            /*Parte relativa a la ultima pestaña*/
            if (test) {
                //Creamos el objeto partida
                juego = new Partida();
                //Colocamos los valores de la matriz
                juego.actualizar_matriz();
                //Llamamos a la funcion que printa
                //Habilitamos la pestaña final
                tabMatrix.setDisable(false);
                String themes;
                //Colocamos el fondo con la regla css
                if (ini_cbTemas.getSelectionModel().selectedItemProperty().getValue() != null) { //Condición de un valor en el choicebox
                    themes = ini_cbTemas.getSelectionModel().selectedItemProperty().getValue().toString(); 
                    Datos.tabla.put(Datos.TEMA, themes); //Rellenar todos los datos
                    //out.println(themes+".png");
                    String image = FXMLDocumentController.class
                            .getResource(themes + "1200.png").toExternalForm();
                    matrix_panelMain.setStyle(
                            "-fx-background-image: url('" + image + "'); -fx-background-position: center center;-fx-background-repeat: stretch;");

                } else {
                    Datos.tabla.put(Datos.TEMA, "nada");
                }
                //Printamos las celdas
                generarceldas();
            }
        }

    }

    @FXML
    private void play() {
        int resultado = juego.buscar_camino();
        if (resultado == 0) {
            play_NoSolucion.setText("No hay camino");//ETIQUETA DE NO HAY CAMINO
        } else {
            juego.actualizar_matriz();
            generarceldas();
            play_NoSolucion.setText("El coste es " + resultado);
        }
    }
    /* -------------------------------------------------
     Fin pestaña de Matriz
     -------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
        ini_cbTemas.setItems(temas);

    }

}
