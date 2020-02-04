package com.company;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;

//import static java.lang.Math.min;

public class Main {

    private static final Integer INF =  Integer.MAX_VALUE / 2;

    public static int[][] matrix; // матрица смежности

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0;

        System.out.println("Введите количество вершин графа");
        n = sc.nextInt();

        matrix = new int[n][n];

        /** Генерация случайной неориентированной матрицы смежности*/
        createAdjacencyMatrix(n);

        System.out.println(" Матрица смежности ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        /** Вычисления радиуса и диаметра графа и определение Центральных и Периферийных вершин*/
        eccentricityCalculat(matrix, n);
        /** Определение степеней вершин графа по Матрице Смежности*/
        degreeCalculationVertices(matrix, n);

        /** Генерация матрицы Инциндентности */
        createIncidentMatrix( matrix, n);
    }

    /** Генерация случайной неориентированной Матрицы Смежности*/
    public static void createAdjacencyMatrix( int n){
        int index = 0;
        double number = 0;

        for(int i = 0; i<n; i++){
            for (int j  = index; j <n ; j++){
                number = Math.random(); // Рандомим случайное значение в диапазоне 0.0 - 1.0
                int resultNum = (int) Math.round (number); // округляем результат к ближайшему целому числу

                if(i!=j){
                    matrix[i][j] = resultNum;
                    matrix[j][i] = resultNum;
                }else{
                    matrix[i][j] = resultNum; // у вершины может быть несколько петлей
                }
                /** */
            }
            index++;
        }
    }

    /** Алгоритм ФЛойда-Уолшера, Вычисление эксцентриситета вершин */
    public  static void eccentricityCalculat(int[][] matrix1, int n){
        int[][] matrix_adjacency = new int[n][n];

        for (int i = 0; i < n; i++){
            System.arraycopy(matrix1[i], 0, matrix_adjacency[i], 0, n);
        }

        for (int i = 0; i <matrix_adjacency.length; i++) {
            for (int j = 0; j < matrix_adjacency[i].length; j++) {
                if(i!=j && matrix_adjacency[i][j]==0){
                    matrix_adjacency[i][j] = INF;
                }
            }
        }

        System.out.println( "Матрица копи " );

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                System.out.print( matrix_adjacency[i][j] + "\t");
            }
            System.out.println();
        }

        for(int i = 0; i<n; i++){
            if(matrix_adjacency[i][i]>0){
                matrix_adjacency[i][i] = 0;// если петли найдены обнуляю значение
            }
        }

        /** Алгоритм Флойда - Уоршела */

        for (int k = 0; k < n; ++k){
            for (int i= 0; i < n; ++i){
                for (int j = 0; j < n; ++j){
                    if(i!=j){
                        if(matrix_adjacency[i][j]!= 0){
                            matrix_adjacency[i][j] = min( matrix_adjacency[i][j], matrix_adjacency[i][k] + matrix_adjacency[k][j] );
                        }else{
                            matrix_adjacency[i][j] = matrix_adjacency[i][k] + matrix_adjacency[k][i];
                        }
                    }
                }
            }
        }

        /** Вычисление эксцентриситета вершин */
        int[] eccentricity = new int[n];
        for (int i = 0; i < n; i++) {
            eccentricity[i] = -1;

            for (int j = 0; j < n; j++) {
                if(matrix_adjacency[i][j]!=INF && eccentricity [i]<matrix_adjacency[i][j] ){
                    eccentricity [i] = matrix_adjacency[i][j];
                }
            }
        }
        /** */

        outTypeVertex(eccentricity);
    }

    /** Нахождение радиуса и диаметра графа, Вывод результатов*/
    public static void outTypeVertex( int[] eccentricity){
        String central_tops = "";
        String peripheral_tops = "";

        int min_r = INF;
        int max_d = 0;

        /** Нахождение радиуса и диаметра графа*/
        for( int i = 1; i<eccentricity.length; i++){
            if(min_r > eccentricity[i] && eccentricity[i]>0){
                min_r = eccentricity[i];
            }
            if( max_d < eccentricity[i] ){
                max_d = eccentricity[i];
            }
        }

        if(min_r != max_d){
            for( int i = 0; i<eccentricity.length; i++){
                if(eccentricity[i]==min_r) {
                    central_tops = central_tops + i + " ";
                }
                if(eccentricity[i] == max_d){
                    peripheral_tops = peripheral_tops + i + " ";
                }
            }
        }else{
            for (int i= 0; i<eccentricity.length; i++ ){
                central_tops = central_tops + i + ", ";
            }
        }

        System.out.println("Радиус графа - "+ min_r);
        System.out.println("Диаметр графа - " + max_d);

        System.out.println("Центральные вершины " + central_tops);

        if(!peripheral_tops.isEmpty()){
            System.out.println("Переферийные вершины " +peripheral_tops);
        }
    }

    /** Подсчёт Степеней вершин по Матрице Смежности */
    public static void degreeCalculationVertices(int[][] matrix1, int n ){
        String type_matrix = "Смежной";
        int[] vertices_degree = new int[n]; // степени вершин без учёта петель
        int[] loops_vertices = new int[n]; // только степени петель вершин

        /**Вычисление степеней вершин графа */
        int index = 0;
        for (int i = 0; i<n; i++ ){
            for (int j = index; j <n; j++){
                if(matrix1[i][j]>0){

                    if (i!=j){
                        vertices_degree[i] = vertices_degree[i] + matrix1[i][j] ;
                        vertices_degree[j] = vertices_degree[j] + matrix1[j][i] ;
                    }else{
                        loops_vertices[i] = matrix1[i][j] * 2; // степени петель для вершины
                    }
                }
            }
            index++;
        }

       /* for (int i =0; i<loops_vertices.length; i++){
            System.out.print(" vertices_degree " +vertices_degree[i]);
            System.out.print(" loops " +loops_vertices[i]);
            System.out.println( );

        }*/

        outTypesVertex(vertices_degree, loops_vertices, type_matrix);
    }


    /** Генерация  Матрицы Инцендентов*/
    public static void createIncidentMatrix(int [][] adjacency_matrix, int n){
        int ribs = countRibs(adjacency_matrix, n);
        int matrix_incen [][] = new int[n][ribs];

        int ind = 0;
        int index_1 = 0;

        for (int i = 0; i< n; i++){
            for (int j = index_1; j<n; j++ ){
                if (adjacency_matrix[i][j] > 0){
                    if(i==j){
                        for ( int k = 0; k <adjacency_matrix[i][j]; k++){
                            matrix_incen[i][ind] = 2;
                            ind++;
                        }
                    }else{
                        for ( int k = 0; k <adjacency_matrix[i][j]; k++){
                            matrix_incen[i][ind] = 1;
                            matrix_incen[j][ind] = 1;
                            ind++;
                        }
                    }
                }
            }
            index_1++;
        }

        degreeCalculationVertices(matrix_incen, n , ribs);

    }


    /** Подсчитываю количество рёбер у графа ( с учётом петель) */
    public static int countRibs(   int[][] adjacency_matrix , int n){
        int count_ribs = 0;
        int index = 0;

        for (int i = 0; i<n; i++) {
            for (int j = index; j < n; j++) {
                count_ribs = count_ribs + adjacency_matrix[i][j];
            }
            index++;
        }
        System.out.println("Количество рёбер - " + count_ribs);
        return count_ribs;
    }


    /** Подсчёт Степеней вершин по Матрице Инциндентности */
    public static void degreeCalculationVertices(int[][] matrix1, int n, int ribs ){
        String type_matrix = "Инциндентной";

        int[] vertices_degree = new int[n]; // степени вершин без учёта петель
        int[] loops_vertices = new int[n]; // только степени петель вершин

        for (int i = 0; i<n; i++){
            for (int j = 0; j<ribs; j++){
                switch (matrix1[i][j]){
                    case 1:{
                        vertices_degree[i] ++;
                        break;
                    }
                    case 2:{
                        loops_vertices[i] = 2;
                        break;
                    }
                }
            }
        }

        outTypesVertex(vertices_degree, loops_vertices, type_matrix);
    }

    /** Список степеней вершин графа */
    public static void outTypesVertex(int[] vertices_degree, int[] loops_vertices, String type_matrix ){
        String vertex_isolated = "";
        String vertex_end = "";
        String vertex_dominant = "";
         HashMap<Integer,String> typeVertex = new HashMap<Integer, String>();
        int vertex_count = vertices_degree.length;

        for (int i = 0; i <vertices_degree.length; i++) {
            switch (vertices_degree[i]){
                case 0:{
                    vertex_isolated = vertex_isolated  + i +  " со степенью "
                            + (vertices_degree[i]  + loops_vertices[i]) + " " ;
                    break;
                }
                case 1:{
                    vertex_end = vertex_end  +  i +  " со степенью "
                            + (vertices_degree[i]  + loops_vertices[i]) + " ";
                }
                default:{
                    if( (vertices_degree[i] == vertex_count -1) ){
                        vertex_dominant = vertex_dominant  + i +  " со степенью "
                                + (vertices_degree[i]  + loops_vertices[i]) + " ";
                    }
                    break;
                }
            }
        }


        boolean flag =true;
        if (vertex_isolated.isEmpty() && vertex_end.isEmpty()
                && vertex_dominant.isEmpty()){
            flag = false;
        }else {
            typeVertex.put(0,vertex_isolated);
            typeVertex.put(1,vertex_end);
            typeVertex.put(6,vertex_dominant);
        }

        if (flag) {
            System.out.println(" Типы у Вершин " + type_matrix +" матрицы " );
            if (!typeVertex.get(0).isEmpty()){
                System.out.println("Изолированные вершины " + typeVertex.get(0)); }

            if (!typeVertex.get(1).isEmpty()){
                System.out.println("Концевые вершины " + typeVertex.get(1)); }

            if (!typeVertex.get(6).isEmpty()){
                System.out.println("Доминирующие вершины " + typeVertex.get(6)); }

        }else{
            System.out.println(" Типы у Вершин " + type_matrix +" матрицы  отсутствуют" );
        }

    }

}
