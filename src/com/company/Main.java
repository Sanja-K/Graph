package com.company;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.min;
import static java.lang.Math.max;

//import static java.lang.Math.min;

public class Main {

    private static final Integer INF =  Integer.MAX_VALUE / 2;;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer matrix[][]; // матрица смежности
        int n = 0;
        double number = 0;

        System.out.println("Введите количестdо вершин графа");
        n = sc.nextInt();

        matrix = new Integer[n][n];
        int r = 0;
        int index = 0;
        int vertex_degree [] = new int [n]; //массив со значениеми степеней вершин
        int ribs = 0; // Количество рёбер графа

        //TODO Переделать Сортировкку вершин по видам в матрице смежности ( без учёта петель)//

        /** Генерация случайной неориентированной матрицы смежности*/
        for(int i = 0; i<n; i++){
            for (int j  = index; j <n ; j++){
                 number = Math.random(); // Рандомим случайное занчение в диапазоне 0.0 - 1.0
                int resultNum = (int) Math.round (number); // округляем результат к ближайшему целому числу
                matrix[i][j] = resultNum;

                if(i!=j){
                    matrix[j][i] = resultNum;
                }

                /**Вычисление степеней верщин графа */
                if(resultNum == 1){
                    ribs ++;

                    vertex_degree[i]++;
                    if(i!=j){ // Если i и j равны, то значит у вершины i есть петля, а это считается как 2 вхождения в вершину
                        vertex_degree[j]++;
                    }else{
                        vertex_degree[i] ++;
                    }
                }
                 /** */
            }
            index++;
        }
        /** */

        System.out.println(" МАТРИЦА СМЕЖНОСТИ ");
        /** Сортировка вершин по видам*/
        for (int i = 0; i<vertex_degree.length; i++){

            switch (vertex_degree[i]){
                case 0: {
                    System.out.println(" Вершина " + i +  " со степенью " + vertex_degree[i] + " является изолированной");
                    break;
                }
                case 1: {
                    System.out.println(" Вершина " + i +  " со степенью " + vertex_degree[i] + " является концевой");
                    break;
                }
                default :{
                    if( (vertex_degree[i] == (n-1) && matrix[i][i]== 0 ) || (vertex_degree[i] == n + 1 && matrix[i][i] == 1) ){
                        System.out.println(" Вершина " + i +  " со степенью " + vertex_degree[i] + " является доминирующей");
                        break;
                    }else {
                        System.out.println(" Доминирующие вершины отсутствуют");
                    }
                }
            }
        }
        /** */



        /** Генерация матрицы инциндентности на основе матрицы смежности */
        //ArrayList<Integer> loop_vertex = new ArrayList<Integer>();
        int loop_vertex[] = new int[n];
        int vertex_degree_inc [] = new int[n];
        int matrix_incen [][] = new int[n][ribs];
            int ind = 0;
            int index_1 = 0;
          for (int i = 0; i< n; i++){
              for (int j = index_1; j<n; j++ ){
                  if (matrix[i][j] == 1){

                        if(i==j){
                            matrix_incen[i][ind] = 2;
                            loop_vertex[i] = j;
                        }else{
                            matrix_incen[i][ind] = 1;
                            matrix_incen [j][ind] = 1;
                        }
                      ind++;
                  }
              }
                index_1++;
          }

        System.out.println(" inceen ");
          for (int i = 0; i<n; i++){
              for (int j = 0; j<ribs; j++){
                  System.out.print(matrix_incen[i][j] + " ");
              }
              System.out.println();
          }

        int val;
        for (int i = 0; i<n; i++){
            for (int j = 0; j<ribs; j++){

                if(matrix_incen[i][j] ==1){
                    vertex_degree_inc [i]++;
                }

            }
        }

        /** Вывод результата*/

        System.out.println(" МАТРИЦА ИНЦЕНДЕНТОВ ");
        for (int i = 0; i <vertex_degree_inc.length; i++) {
             switch (vertex_degree_inc[i]){

                 case 0:{
                     System.out.println(" Вершина " + i +  " со степенью "   + (vertex_degree_inc[i] + loop_vertex[i]) + " является изолированной");
                     break;
                 }
                 case 1:{
                     System.out.println(" Вершина " + i +  " со степенью "   + (vertex_degree_inc[i] + loop_vertex[i]) + " является концевой");
                 }
                 default:{

                     if( (vertex_degree_inc[i] == (n-1)) ){
                         System.out.println(" Вершина " + i +  " со степенью " + (vertex_degree_inc[i] + loop_vertex[i]) + " является доминирующей");
                         break;
                     }else {
                         System.out.println(" Доминирующие вершины отсутствуют");
                     }
                 }
             }

        }

        /** */


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("sm");
        for (int j = 0; j < vertex_degree.length; j++) {
            System.out.print(vertex_degree[j] + "\t");
        }

        /** Обнудяем Значения диагональных ячеек т.к. они не учавствуют в вычислении матрицы расстояний*/
        for (int i = 0; i<n; i++){
            matrix[i][i] = 0;
        }
        /** */

        System.out.println(" sttepen ");

        /** Заменяем все не диагональные нули, значениями бесконечности */
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(i!=j && matrix[i][j]==0){
                    matrix[i][j] = INF;
                }
            }
        }


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
      //  System.out.println( );

        algoritm(matrix, n);

    }

    /** Ал*/
    public  static void algoritm(Integer[][] matrix1, int n){

        Integer [][] dist = new Integer [n][n];
        int e[] =new int[n]; // Эксцентриситет вершин

        for (int i = 0; i < n; i++){
            System.arraycopy(matrix1[i], 0, dist[i], 0, n);
        }


        for(int i = 0; i<n; i++){
            matrix1[i][i] = 0;
        }
    /** Аолгоритм Флойда - Уоршела */
    for (int k = 0; k < n; ++k){
        for (int i= 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                if(i!=j){
                   // if (matrix1[i][k] < INF && matrix1[k][j] < INF){
                    if(matrix1[i][j]!= 0){
                        matrix1[i][j] = min( matrix1[i][j], matrix1[i][k] + matrix1[k][j] );
                    }else{
                        matrix1[i][j] = matrix1[i][k] + matrix1[k][i];
                    }
                }
            }
        }
    }
    /** */

        System.out.println(" matr " );

        /** Вычисление эксцентриситета вершин */
    int eccentricity[] = new int[n];
        for (int i = 0; i < n; i++) {
            eccentricity[i] = matrix1[i][0];

            for (int j = 0; j < n; j++) {
                if(eccentricity [i]<matrix1[i][j]){
                    eccentricity [i] = matrix1[i][j];
                }
                System.out.print( matrix1[i][j] + "\t" );
            }
            System.out.println( );
        }
        /** */

        for( int i = 0; i<eccentricity.length; i++){

            System.out.println(" eccen " +eccentricity[i]);
        }
            String central_tops = "" ;
            String peripheral_tops = "";

            int min_r = eccentricity[0];
            int max_d = eccentricity[0];


        for( int i = 1; i<eccentricity.length; i++){
            if(min_r > eccentricity[i]){
                min_r = eccentricity[i];
            } else{ if( max_d < eccentricity[i]){
                        max_d = eccentricity[i];
                    }
            }
           // System.out.println(" eccen " +eccentricity[i]);
        }

        if(min_r != max_d){
            for( int i = 0; i<eccentricity.length; i++){
                if(eccentricity[i]==min_r) {
                    central_tops = central_tops + i + " ";
                }else{ if(eccentricity[i] == max_d){
                            peripheral_tops = peripheral_tops + i + " ";
                        }
                }
            }

        }else{
            central_tops = " Всё вершины графа являются центральными !";
        }




        System.out.println("Радиус - "+ min_r);
        System.out.println("Диаметр - " + max_d);

        System.out.println("central_tops " + central_tops);
        System.out.println("peripheral_tops " +peripheral_tops);
  /*      for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[i].length; j++) {
                System.out.print(dist[i][j] + "\t");
            }
            System.out.println();
        }*/

    }
}
