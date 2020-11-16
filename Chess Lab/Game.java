import java.awt.*;
import java.util.*;

/**
 * Manages the chess game, including creating the board and the pieces,
 * managing moves, and printing a log of all the moves that occured in the game.
 * 
 * @author Utkarsh Priyam
 * @version v1
 */
public class Game 
{
    // methods for class Game
    private static String alphabet = "abcdefgh";
    private static String moves = "";
    private static int[] safety;
    
    /**
     * Runs the Chess game
     * @param args arguments from the command line
     */
    public static void main(String [ ] args)
    {
        boolean gameOver = false;
        int gameCounter = 1;
        String spacing = ".  ";
        String gameStatus;
        
        // Create game baord
        Board board = new Board();
        
        // Create display for game
        BoardDisplay display = new BoardDisplay(board);
        board.setDisplay( display );

        // Kings on board
        Piece blackKing = new King(Color.BLACK, "black_king.gif");
        blackKing.putSelfInGrid(board, new Location(0, 4));

        Piece whiteKing = new King(Color.WHITE, "white_king.gif");
        whiteKing.putSelfInGrid(board, new Location(7, 4));

        // Rooks on board
        Piece blackRook1 = new Rook(Color.BLACK, "black_rook.gif", "A");
        blackRook1.putSelfInGrid(board, new Location(0, 0));
        Piece blackRook2 = new Rook(Color.BLACK, "black_rook.gif", "H");
        blackRook2.putSelfInGrid(board, new Location(0, 7));

        Piece whiteRook1 = new Rook(Color.WHITE, "white_rook.gif", "A");
        whiteRook1.putSelfInGrid(board, new Location(7, 0));
        Piece whiteRook2 = new Rook(Color.WHITE, "white_rook.gif", "H");
        whiteRook2.putSelfInGrid(board, new Location(7, 7));
        
        // Pawns on board
        for ( int i = 0; i < 8; i++ )
        {
            Piece blackPawn = new Pawn(Color.BLACK, "black_pawn.gif");
            blackPawn.putSelfInGrid(board, new Location(1, i));
            
            Piece whitePawn = new Pawn(Color.WHITE, "white_pawn.gif");
            whitePawn.putSelfInGrid(board, new Location(6, i));
        }
        
        // Knights on board
        Piece blackKnight1 = new Knight(Color.BLACK, "black_knight.gif");
        blackKnight1.putSelfInGrid(board, new Location(0, 1));
        Piece blackKnight2 = new Knight(Color.BLACK, "black_knight.gif");
        blackKnight2.putSelfInGrid(board, new Location(0, 6));

        Piece whiteKnight1 = new Knight(Color.WHITE, "white_knight.gif");
        whiteKnight1.putSelfInGrid(board, new Location(7, 1));
        Piece whiteKnight2 = new Knight(Color.WHITE, "white_knight.gif");
        whiteKnight2.putSelfInGrid(board, new Location(7, 6));
        
        // Bishops on board
        Piece blackBishop1 = new Bishop(Color.BLACK, "black_bishop.gif");
        blackBishop1.putSelfInGrid(board, new Location(0, 2));
        Piece blackBishop2 = new Bishop(Color.BLACK, "black_bishop.gif");
        blackBishop2.putSelfInGrid(board, new Location(0, 5));

        Piece whiteBishop1 = new Bishop(Color.WHITE, "white_bishop.gif");
        whiteBishop1.putSelfInGrid(board, new Location(7, 2));
        Piece whiteBishop2 = new Bishop(Color.WHITE, "white_bishop.gif");
        whiteBishop2.putSelfInGrid(board, new Location(7, 5));
        
        // Queens on board
        Piece blackQueen = new Queen(Color.BLACK, "black_queen.gif");
        blackQueen.putSelfInGrid(board, new Location(0, 3));

        Piece whiteQueen = new Queen(Color.WHITE, "white_queen.gif");
        whiteQueen.putSelfInGrid(board, new Location(7, 3));

        // Testing
        Player p1 = new HumanPlayer(board, "White", Color.WHITE, display);
        Player p2 = new HumanPlayer(board, "Black", Color.BLACK, display);
        display.showBoard();
        
        while ( ! gameOver )
        {
            System.out.print( gameCounter + spacing );
            
            nextTurn( board, display, p1 );
            nextTurn( board, display, p2 );
            
            gameCounter++;
            if ( gameCounter == 10 )
                spacing = ". ";
            System.out.println();
        }
    }

    /**
     * Executes the next move of the game and prints the move in the log.
     * @param board     the game board
     * @param display   the display for the game
     * @param player    the player whose turn it is
     */
    private static void nextTurn(Board board, BoardDisplay display, Player player)
    {
        String name = player.getName().toLowerCase();
        if ( name.length() > 0 )
            name = name.substring(0,1).toUpperCase() + name.substring(1);
        display.setTitle( name + "'s Turn");
        Move move = player.nextMove();
        board.executeMove( move );
        display.clearColors();
        display.setColor( move.getDestination(), Color.YELLOW );
        display.setColor( move.getSource(), Color.WHITE );
        display.showBoard();
        safety = board.evaluateGameStatus();
        printMove( move, player.getColor() );
        try
        {
            Thread.sleep(500);
        }
        catch ( InterruptedException e )
        {
            // Do Nothing
        }
        display.showBoard();
    }
    
    /**
     * Prints the specified move
     * @param m     the move to print
     * @param col   the color of the player whose move is being printed
     */
    private static void printMove( Move m, Color col )
    {
        String piece = "";
        String victim = "";
        String promotion = "";
        
        Piece p = m.getPiece();
        Piece v = m.getVictim();
        Location s = m.getSource();
        Location l = m.getDestination();
        
        String move;
        String spacing = "            ";
        
        if ( p instanceof King && Math.abs( s.getCol() - l.getCol() ) == 2 )
        {
            move = "0-0";
            if ( s.getCol() - l.getCol() > 0 )
                move += "-0";
        }
        else
        {
            int r = l.getRow();
            int c = l.getCol();
            
            if ( p instanceof Pawn && ( r == 0 || r == 7 ) )
                promotion = "Q";
            
            String dest = alphabet.substring(c,c+1) + (8 - r);
            if ( p instanceof King )
                piece = "K";
            else if ( p instanceof Rook )
                piece = "R";
            else if ( p instanceof Queen )
                piece = "Q";
            else if ( p instanceof Bishop )
                piece = "B";
            else if ( p instanceof Knight )
                piece = "N";
            
            c = s.getCol();
            
            if ( v != null )
            {
                victim = "x";
                if ( p instanceof Pawn )
                    piece = alphabet.substring(c,c+1);
            }
            
            move = piece + victim + dest + promotion;
            int i;
            if ( Color.WHITE.equals(col) )
                i = safety[1];
            else
                i = safety[0];
            while ( i > 0 )
            {
                move += "+";
                i--;
            }
        }
        spacing = spacing.substring( 0, 7 - move.length() );
        System.out.print( move + spacing );
    }
}

//
// This is a variation of the Game Class which has the option to
// choose the type of game to play (human v human, comp v human, etc.)
// I am leaving it here instead of in its own class because
// I don't want to JavaDoc it, but I also don't want to delete it.
// 
// import java.awt.*;
// import java.util.*;
// 
// /**
//  * Write a description of class Chess here.
//  * 
//  * @author (your name)
//  * @version (a version number or a date) 
//  */
// public class Chess 
// {
//     // methods for class Game
//     private String alphabet;
//     private String moves;
//     private int[] safety;
//     private Player p1, p2;
//     private Board board;
//     private BoardDisplay display;
//     
//     /**
//      * 
//      */
//     public Chess( int gamemode )
//     {
//         board = new Board();
//         display = new BoardDisplay(board);
//         board.setDisplay( display );
//         alphabet = "abcdefgh";
//         moves = "";
//         if ( gamemode < 0 || gamemode >= 4 )
//             gamemode = 0;
//         if ( gamemode < 2 )
//             p1 = new HumanPlayer(board, "White", Color.WHITE, display);
//         else
//             p1 = new SmartestPlayer(board, "White", Color.WHITE);
//         if ( gamemode % 2 == 0 )
//             p2 = new HumanPlayer(board, "Black", Color.BLACK, display);
//         else
//             p2 = new SmartestPlayer(board, "Black", Color.BLACK);
//     }
//     
//     /**
//      * Write method description here.
//      *
//      * @param args arguments from the command line
//      */
//     public void main(String [ ] args)
//     {
//         boolean gameOver = false;
//         int gameCounter = 1;
//         String spacing = ".  ";
//         String gameStatus;
// 
//         // Kings on board
//         Piece blackKing = new King(Color.BLACK, "black_king.gif");
//         blackKing.putSelfInGrid(board, new Location(0, 4));
// 
//         Piece whiteKing = new King(Color.WHITE, "white_king.gif");
//         whiteKing.putSelfInGrid(board, new Location(7, 4));
// 
//         // Rooks on board
//         Piece blackRook1 = new Rook(Color.BLACK, "black_rook.gif", "A");
//         blackRook1.putSelfInGrid(board, new Location(0, 0));
//         Piece blackRook2 = new Rook(Color.BLACK, "black_rook.gif", "H");
//         blackRook2.putSelfInGrid(board, new Location(0, 7));
// 
//         Piece whiteRook1 = new Rook(Color.WHITE, "white_rook.gif", "A");
//         whiteRook1.putSelfInGrid(board, new Location(7, 0));
//         Piece whiteRook2 = new Rook(Color.WHITE, "white_rook.gif", "H");
//         whiteRook2.putSelfInGrid(board, new Location(7, 7));
//         
//         // Pawns on board
//         for ( int i = 0; i < 8; i++ )
//         {
//             Piece blackPawn = new Pawn(Color.BLACK, "black_pawn.gif");
//             blackPawn.putSelfInGrid(board, new Location(1, i));
//             
//             Piece whitePawn = new Pawn(Color.WHITE, "white_pawn.gif");
//             whitePawn.putSelfInGrid(board, new Location(6, i));
//         }
//         
//         // Knights on board
//         Piece blackKnight1 = new Knight(Color.BLACK, "black_knight.gif");
//         blackKnight1.putSelfInGrid(board, new Location(0, 1));
//         Piece blackKnight2 = new Knight(Color.BLACK, "black_knight.gif");
//         blackKnight2.putSelfInGrid(board, new Location(0, 6));
// 
//         Piece whiteKnight1 = new Knight(Color.WHITE, "white_knight.gif");
//         whiteKnight1.putSelfInGrid(board, new Location(7, 1));
//         Piece whiteKnight2 = new Knight(Color.WHITE, "white_knight.gif");
//         whiteKnight2.putSelfInGrid(board, new Location(7, 6));
//         
//         // Bishops on board
//         Piece blackBishop1 = new Bishop(Color.BLACK, "black_bishop.gif");
//         blackBishop1.putSelfInGrid(board, new Location(0, 2));
//         Piece blackBishop2 = new Bishop(Color.BLACK, "black_bishop.gif");
//         blackBishop2.putSelfInGrid(board, new Location(0, 5));
// 
//         Piece whiteBishop1 = new Bishop(Color.WHITE, "white_bishop.gif");
//         whiteBishop1.putSelfInGrid(board, new Location(7, 2));
//         Piece whiteBishop2 = new Bishop(Color.WHITE, "white_bishop.gif");
//         whiteBishop2.putSelfInGrid(board, new Location(7, 5));
//         
//         // Queens on board
//         Piece blackQueen = new Queen(Color.BLACK, "black_queen.gif");
//         blackQueen.putSelfInGrid(board, new Location(0, 3));
// 
//         Piece whiteQueen = new Queen(Color.WHITE, "white_queen.gif");
//         whiteQueen.putSelfInGrid(board, new Location(7, 3));
// 
//         // Testing
//         display.showBoard();
//         
//         while ( ! gameOver )
//         {
//             moves += gameCounter + spacing;
//             
//             nextTurn( board, display, p1 );
//             nextTurn( board, display, p2 );
//             
//             gameCounter++;
//             if ( gameCounter == 10 )
//                 spacing = ". ";
//             moves += "\n";
//         }
//         System.out.println( moves );
//     }
// 
//     /**
//      * 
//      */
//     private void nextTurn(Board board, BoardDisplay display, Player player)
//     {
//         String name = player.getName();
//         if ( name.length() > 0 )
//             name = name.substring(0,1).toUpperCase() + name.substring(1);
//         display.setTitle( name + "'s Turn");
//         Move move = player.nextMove();
//         board.executeMove( move );
//         display.clearColors();
//         display.setColor( move.getDestination(), Color.YELLOW );
//         display.setColor( move.getSource(), Color.WHITE );
//         display.showBoard();
//         safety = board.evaluateGameStatus();
//         printMove( move, player.getColor() );
//         try
//         {
//             Thread.sleep(500);
//         }
//         catch ( InterruptedException e )
//         {
//             // Do Nothing
//         }
//         display.showBoard();
//     }
//     
//     private void printMove( Move m, Color col )
//     {
//         String piece = "";
//         String victim = "";
//         String promotion = "";
//         
//         Piece p = m.getPiece();
//         Piece v = m.getVictim();
//         Location s = m.getSource();
//         Location l = m.getDestination();
//         
//         if ( p instanceof King && Math.abs( s.getCol() - l.getCol() ) == 2 )
//         {
//             if ( s.getCol() - l.getCol() > 0 )
//                 moves += "0-0-0";
//             else
//                 moves += "0-0";
//             return;
//         }
//         
//         int r = l.getRow();
//         int c = l.getCol();
//         
//         if ( p instanceof Pawn && ( r == 0 || r == 7 ) )
//             promotion = "Q";
//         
//         String dest = alphabet.substring(c,c+1) + (8 - r);
//         if ( p instanceof King )
//             piece = "K";
//         else if ( p instanceof Rook )
//             piece = "R";
//         else if ( p instanceof Queen )
//             piece = "Q";
//         else if ( p instanceof Bishop )
//             piece = "B";
//         else if ( p instanceof Knight )
//             piece = "N";
//         
//         c = s.getCol();
//         
//         if ( v != null )
//         {
//             victim = "x";
//             if ( p instanceof Pawn )
//                 piece = alphabet.substring(c,c+1);
//         }
//         
//         String move = piece + victim + dest + promotion;
//         String spacing = "            ";
//         int i;
//         if ( Color.WHITE.equals(col) )
//             i = safety[1];
//         else
//             i = safety[0];
//         while ( i > 0 )
//         {
//             move += "+";
//             i--;
//         }
//         spacing = spacing.substring( 0, 7 - move.length() );
//         moves += move + spacing;
//     }
// }
