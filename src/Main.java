import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static HashMap<Character, String> tokens = new HashMap<Character, String>();
    public static HashMap<String, String> tokensCompuestos = new HashMap<String, String>();
    public static ArrayList<String> palabrasReservadas = new ArrayList<String>(); // Create an ArrayList object
    public static boolean comentario = false;

    public static void inicializar(){
        palabrasReservadas.add("funcion_principal");
        palabrasReservadas.add("fin_principal");
        palabrasReservadas.add("leer");
        palabrasReservadas.add("imprimir");
        palabrasReservadas.add("si");
        palabrasReservadas.add("entonces");
        palabrasReservadas.add("fin_si");
        palabrasReservadas.add("si_no");
        palabrasReservadas.add("mientras");
        palabrasReservadas.add("hacer");
        palabrasReservadas.add("fin_mientras");
        palabrasReservadas.add("para");
        palabrasReservadas.add("fin_para");
        palabrasReservadas.add("seleccionar");
        palabrasReservadas.add("entre");
        palabrasReservadas.add("caso");
        palabrasReservadas.add("romper");
        palabrasReservadas.add("defecto");
        palabrasReservadas.add("fin_seleccionar");
        palabrasReservadas.add("estructura");
        palabrasReservadas.add("fin_estructura");
        palabrasReservadas.add("funcion");
        palabrasReservadas.add("retornar");
        palabrasReservadas.add("fin_funcion");
        palabrasReservadas.add("verdadero");
        palabrasReservadas.add("falso");
        palabrasReservadas.add("entero");
        palabrasReservadas.add("caracter");
        palabrasReservadas.add("booleano");
        palabrasReservadas.add("real");
        palabrasReservadas.add("cadena");

        tokens.put('+',"tk_mas");
        tokens.put('-',"tk_menos");
        tokens.put('*',"tk_mult");
        tokens.put('/',"tk_div");
        tokens.put('%',"tk_mod");
        tokens.put('=',"tk_asig");
        tokens.put('<',"tk_menor");
        tokens.put('>',"tk_mayor");
        tokens.put(':',"tk_dosp");
        tokens.put(';',"tk_pyc");
        tokens.put(',',"tk_coma");
        tokens.put('.',"tk_punto");
        tokens.put('(',"tk_par_izq");
        tokens.put(')',"tk_par_der");
        tokens.put('!',"tk_neg");

        tokensCompuestos.put("<=","tk_menor_igual");
        tokensCompuestos.put(">=","tk_mayor_igual");
        tokensCompuestos.put("==","tk_igual");
        tokensCompuestos.put("!=","tk_dif");
        tokensCompuestos.put("&&","tk_y");
        tokensCompuestos.put("||","tk_o");
    }
    public static boolean esLetra(char i){
        int asci = (int) i;
        if((asci >= 65 && asci <= 90) || (asci >= 97 && asci <= 122)){
            return true;
        }
        return false;
    }

    public static boolean esNumero(char i){
        int asci = (int) i;
        if(asci >= 48 && asci <= 57){
            return true;
        }
        return false;
    }

    public static void erroLexico(int linea, int columna){
        System.out.println(">>> Error lexico (linea: "+linea+", posicion: "+columna+")");
        System.exit(0);
    }

    public static int delta (int estado, char c){
        switch (estado){
            case 0: if (esLetra(c)) return 1;
                    else if(esNumero(c)) return 2;
                    else if(c == ' ') return 0;
                    else if(c == '<') return 5;
                    else if(c == '>') return 5;
                    else if(c == '=') return 9;
                    else if(c == '!') return 11;
                    else if(c == '/') return 13;
                    else if(c == '|') return 14;
                    else if(c == '*') return 15;
                    else if(c ==  '"') return 16;
                    else if(c == '\'') return 18;
                    else if(c == '\t') return 0;
                    else if(c == '&') return 23;
                    else if(tokens.get(c) != null) return -1;
                    else return -11;
            case 1: if(esLetra(c) || esNumero(c) || c == '_') return 1;
                    else if(c == ' ') return -1;
                    else return -6;
            case 2: if(esNumero(c)) return 2;
                    else if(c == '.') return 3;
                    else return -5;
            case 3: if(esNumero(c)) return 24;
                    else return -12;
            case 4: if(c == '<') return 5;
                    else if(c == '>') return 5;
                    else if(c == '=') return 9;
                    else if(c == '!') return 11;
                    else if(c == '/') return 13;
                    else if(c == '|') return 14;
                    else if(c == '*') return 15;
                    else if(c ==  '"') return 16;
                    else if(c == '\'') return 18;
                    else return -1;
            case 5: if(c == '=') return -10;
                    else return -7;
            case 6: return -1;
            //case 7: if(c == '=') return -10;
              //      else return -7;
            case 8: return -1;
            case 9: if(c == '=') return -10;
                    else return -7;
            case 10: return -1;
            case 11: if(c == '=') return -10;
                    else return -7;
            case 12: return -1;
            case 13: if(c == '/') return -2;
                    else if(c == '*') return -3;
                    else if( c == ' ') return -7;
                    else return -7;
            case 14: if(c == '|') return -10;
                    else return -11;
            case 15: if(c == '/') return -3;
                    else return -7;
            case 16: if(c == '"') return -4;
                    else if(esNumero(c) || esLetra(c) || c == '_' || c == ' ') return 16;
                    else if(c == '\\') return 17;
                    else return 0;
            case 17: if(c == 'n') return 16;
                    else return -11;
            case 18: if(esLetra(c) || esNumero(c) || c == '_' || c == ' ' ) return 19;
                    else if(c == '\\') return 20;
                    else return -11;
            case 19: if(c == '\'') return -8;
                    else return -11;
            case 20: if(c == 'n') return 19;
                    else return -11;
            case 21: if(c == '*') return 22;
                    else return 21;
            case 22: if(c == '/') return -3;
                    else return 21;
            case 23: if(c == '&') return -10;
                    else return -11;
            case 24: if(esNumero(c)) return 24;
                    else return -9;
        }
        return -1;
    }

    public static void generarTokens(String linea, int fila) {
        Scanner scanner = new Scanner(linea).useDelimiter("");
        int estado = 0;
        int columna = 1;
        String word = "";
        boolean verificar = false;
        char character = ' ';

        if(comentario){
            estado = 21;
        }

        while (scanner.hasNext()) {
            if(!verificar){

                character = scanner.next().charAt(0);
            }else{
                verificar = false;
            }

            estado = delta(estado, character);
            //System.out.println("estado: " + estado);


            if (estado == -1) {
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;
                if(palabrasReservadas.contains(word)){
                    token.tipo = word;
                    System.out.println(token);
                }else if (tokens.get(character) != null) {
                    token.tipo = tokens.get(character);
                    System.out.println(token);
                }else {
                    token.tipo = "id";
                    token.lexema = word;
                    System.out.println(token);
                }
                word = "";
                estado = 0;
            }else if(estado == 0){

            }else if(estado == -2){
                break;
            }else if(estado == -3){
                if(character == '*'){
                    comentario = true;
                    estado = 21;
                }else if(character == '/'){
                    comentario = false;
                    word = "";
                    estado = 0;
                }
            }else if(estado == -4){
                word += character;
                columna++;
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = "tk_cadena";
                token.lexema = word;
                System.out.println(token);

                //verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -5){
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = "tk_entero";
                token.lexema = word;
                System.out.println(token);

                verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -6){
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                if(palabrasReservadas.contains(word)){
                    token.tipo = word;
                }else{
                    token.tipo = "id";
                    token.lexema = word;
                }
                System.out.println(token);

                verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -7){
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = tokens.get(word.charAt(0));
                System.out.println(token);

                verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -8){
                word += character;
                columna++;
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = "tk_caracter";
                token.lexema = word;
                System.out.println(token);

                //verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -9){
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = "tk_real";
                token.lexema = word;
                System.out.println(token);

                verificar = true;
                word = "";
                estado = 0;
                columna--;
            }else if(estado == -10){
                word += character;

                Token token = new Token();
                token.fila = fila;

                //int largo = word.length();
                token.columna = columna - 1;
                token.tipo = tokensCompuestos.get(word);
                System.out.println(token);

                word = "";
                estado = 0;
            }else if(estado == -11){
                erroLexico(fila, columna-word.length());
            }else if(estado == -12){
                Token token = new Token();
                token.fila = fila;
                int largo = word.length();
                token.columna = columna - largo;

                token.tipo = "tk_entero";
                linea = character + linea.substring(columna);
                scanner = new Scanner(linea).useDelimiter("");

                character = word.substring(word.length()-1).charAt(0);
                token.lexema = word.substring(0,word.length()-1);
                System.out.println(token);

                verificar = true;
                word = "";
                estado = 0;
                columna = columna - 2;
            }else{
                word += character;
                //System.out.println("word: " + word);
            }

            columna++;
        }
        if(word.length() > 0 && (word.charAt(0) == '\'' || word.charAt(0) == '"')){
            erroLexico(fila, columna-word.length());
        }
    }

    public static void main(String[] args) throws IOException {
        inicializar();
        Scanner scanner = new Scanner(System.in);
        int fila = 1;

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //boolean b = true;
        while(scanner.hasNextLine()){
            generarTokens(scanner.nextLine()+' ', fila);
            //generarTokens(br.readLine()+' ', fila);
            fila++;
            //b = br.ready();
        }
    }
}

class Token {
    public int fila;
    public int columna;

    public String lexema = "";

    public String tipo;  //tipo es: ID, ENTERO, REAL ...

    @Override
    public String toString() {
        if(!lexema.equals("")){
            return "<" + this.tipo + "," + this.lexema + "," + this.fila + "," + this.columna + ">";
        }else {
            return "<" + this.tipo + "," + this.fila + "," + this.columna + ">";
        }
    }
}
