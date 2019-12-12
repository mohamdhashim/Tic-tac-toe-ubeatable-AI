/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package words_game;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class Words_game {

    /**
     * @param args the command line arguments
     */
    static class move
    {
        public int row,col;
    
    };
    
    static char player = 'o',ai = 'x';
    
    static  Boolean isMovesleft(char board[][])
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(board[i][j]=='_')
                    return true;
        
        return false;
    }

    static int evaluate(char board[][])
    {
        //check raws if anyone has won;
        for(int i=0;i<3;i++)
        {
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2])
                if(board[i][0]==player)
                    return -10;
                else if(board[i][0]==ai)
                    return +10;
            
        }
        
        //check coulms if anyone has won;
        for(int j=0;j<3;j++)
        {
            if(board[0][j] == board[1][j] && board[1][j] == board[2][j])
                if(board[0][j] == player)
                    return -10;
                else if(board[0][j] == ai)
                    return +10; 
            
        }
        // check [0][0] diagonal;
        if(board[0][0]==board[1][1] && board[1][1]==board[2][2])
           if(board[0][0]==player)
               return -10;
           else if(board[0][0]==ai)
               return +10;
        
        //check [0][2] diagonal;
        if(board[0][2]==board[1][1] && board[1][1]==board[2][0])
           if(board[0][2]==player)
               return -10;
           else if(board[0][2]==ai)
               return +10;   
        
        // if no one wins on the board 
        return 0;
    }
    
    
    
    static int minimax(char board[][],int dep , Boolean isAI)
    {
        //Evaluate board to see who has one in current node x or O
        int score = evaluate(board);
        
        // if ai(maximizer) has won or if player (minimizer) has won;
        if(score == 10 || score == -10)
            return score - dep;
        
        
   
        //if game is tie;
        if(isMovesleft(board) == false)
            return 0-dep;
        
        //check if AI(maximizer) turn 
        if(isAI)
        {
            int max = -1000;
            
            //loop on all empty places;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    // chec if cell empty
                    if(board[i][j]=='_')
                    {
                        // make the move
                        board[i][j]= ai;
                        
                        //its player turn 
                        //we want to minimize his socre;
                        
                        max = Math.max(max,minimax(board, dep + 1, !isAI));
                        
                        //make cell empty again (undo move)
                        board[i][j] = '_';
                    }
                }
            }
            return max;
        }else
        {
            int min = 1000;
            
            //loop on all empty places;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    // chec if cell empty
                    if(board[i][j]=='_')
                    {
                        // make the move
                        board[i][j]= player;
                        
                        //its AI turn 
                        //we want to minimize his socre;
                        
                        min = Math.min(min,minimax(board, dep+1, !isAI));
                        
                        //make cell empty again (undo move)
                        board[i][j] = '_';
                    }
                }
            }
            return min;
        }
    }
    
    
    static move findBestmove(char board[][])
    {
        int max_move = -1000;
        move bestMove = new move();
        bestMove.row = -1;
        bestMove.col = -1;
        
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]=='_')
                {
                    
                    board[i][j]= ai;
                    
                    int moveVal = minimax(board, 0, false);
                    
                    board[i][j]='_';
                    
                    if(moveVal > max_move)
                    {
                        bestMove.col = j;
                        bestMove.row = i;
                        max_move = moveVal;
                    }
                }
            }
        }
        System.out.printf("The value of the best Move " +  
                             "is : %d\n\n", max_move); 
  
         return bestMove; 
    }
    public  static void display(char board[][])
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                System.out.print(board[i][j]+" "); 
            }
                System.out.println();
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        char board[][] = {{ '_', '_', '_' }, 
                          { '_', '_', '_' }, 
                          { '_', '_', '_' }}; 
        
        int x,y;
        Scanner s = new Scanner(System.in);
        
        Boolean turn = false;
        for(int i=0;i<9;i++)
        {
            if(turn)
            {
               turn = false;
               move bestMove = findBestmove(board);  
               board[bestMove.row][bestMove.col]='o';
                display(board);
            }else
            {
                turn = true;
                x = s.nextInt();
                y = s.nextInt();               
                board[x][y]='x';
                display(board);
            }
        }
        move bestMove = findBestmove(board); 

        System.out.printf("The Optimal Move is :\n"); 
        System.out.printf("ROW: %d COL: %d\n\n",  
                   bestMove.row, bestMove.col );         
    }
    
}
