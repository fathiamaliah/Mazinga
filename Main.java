/*
 * 215150201111018_FATHIA AMALIAH
 * 215150201111021_SAFIR MAGENTA PUTRI BASTOMI
 */

package mazinga;

import java.util.Scanner;

class Board 
{
    private int sizeX = 0;
    private int sizeY = 0;
    private Coordinate[][] board;
    private Coordinate start;
    private Coordinate end;

    Board(int size) 
    {
        this.board = new Coordinate[size][size];
        this.sizeX = size;
        this.sizeY = size;
    }

    Board(int width, int height) 
    {
        this.board = new Coordinate[width][height];
        this.sizeX = width;
        this.sizeY = height;
    }

    public static class Step 
    {
        private int[][] stepsMap;

        Step(int[][] stepsMap) 
        {
            this.stepsMap = stepsMap;
        }

        public int[][] getStepsMap() 
        {
            return stepsMap;
        }
    }

    public int getSizeX() 
    {
        return sizeX;
    }

    public int getSizeY() 
    {
        return sizeY;
    }

    public void addRoad(String[] roads) 
    {
        for (String road : roads) 
        {
            String[] tempStr = road.split(",");
            int x = Integer.parseInt(tempStr[0]);
            int y = Integer.parseInt(tempStr[1]);
            this.board[x][y] = new Coordinate(x, y);
        }

        String[] tempStrStart = roads[0].split(",");
        int startX = Integer.parseInt(tempStrStart[0]);
        int startY = Integer.parseInt(tempStrStart[1]);
        this.start = this.board[startX][startY];

        String[] tempStrEnd = roads[roads.length - 1].split(",");
        int endX = Integer.parseInt(tempStrEnd[0]);
        int endY = Integer.parseInt(tempStrEnd[1]);
        this.end = this.board[endX][endY];

        generateNeighborsMap();
    }
    
    private void generateNeighborsMap() 
    {
        if ( this.sizeX == 0 || this.sizeY == 0 || this.board == null)  return;

        for (int i = 0; i < this.sizeX; i++) 
        {
            for (int j = 0; j < this.sizeY; j++) 
            {
                Coordinate coordinate = this.board[i][j];
                if (coordinate == null)
                {
                    continue;
                }
                if (i > 0) 
                {
                    coordinate.getNeighbors().setLeftSide(this.board[i - 1][j]);
                }
                if (i < this.sizeX - 1) 
                {
                    coordinate.getNeighbors().setRightSide(this.board[i + 1][j]);
                }
                if (j > 0) 
                {
                    coordinate.getNeighbors().setDownSide(this.board[i][j - 1]);
                }
                if (j < this.sizeY - 1) 
                {
                    coordinate.getNeighbors().setUpSide(this.board[i][j + 1]);
                }
            }
        }
    }

    public void generateBoard(Step track) 
    {
        int[][] stepsMap = track.getStepsMap();
        for (int y = this.board.length - 1; y > -1; y--) 
        {
            for (int x = 0; x < this.board[y].length; x++) 
            {
                if (this.board[x][y] == null) 
                {
                    System.out.print("[XX]");
                } 
                else 
                {
                    int number = stepsMap[x][y];
                    System.out.print(String.format("[%02d]", number));
                }
            }
            System.out.print("\n");
        }
    }

    public void showBoard() 
    {
        generateBoard(new Step(new int[this.sizeX][this.sizeY]));
    }

    public boolean isGoal(Coordinate coord) 
    {
        return coord.equals(this.end);
    }

    public Coordinate getStart() 
    {
        return this.start;
    }

    public Coordinate getEnd() 
    {
        return this.end;
    }

}

interface ComparableObject 
{
    public int compareTo(Object o);
}

class Coordinate 
{
    private int x;
    private int y;
    private Neighbors neighbors;

    public class Neighbors 
    {
        private Coordinate up;
        private Coordinate down;
        private Coordinate left;
        private Coordinate right;

        public Neighbors(Coordinate up, Coordinate down, Coordinate left, Coordinate right) 
        {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        public Coordinate getUpSide() 
        {
            return up;
        }

        public Coordinate getDownSide() 
        {
            return down;
        }

        public Coordinate getLeftSide() 
        {
            return left;
        }

        public Coordinate getRightSide() 
        {
            return right;
        }

        public void setUpSide(Coordinate up) 
        {
            this.up = up;
        }

        public void setDownSide(Coordinate down) 
        {
            this.down = down;
        }

        public void setLeftSide(Coordinate left) {
            this.left = left;
        }

        public void setRightSide(Coordinate right) 
        {
            this.right = right;
        }

        @Override
        public String toString() 
        {
            return "Neighbors{" +
                    "up=" + up +
                    ", down=" + down +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public Coordinate(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.neighbors = new Neighbors(null, null, null, null);
    }

    public int getCoordinateX() {
        return x;
    }

    public int getCoordinateY() {
        return y;
    }

    public Neighbors getNeighbors() 
    {
        return neighbors;
    }

    public void setNeighbors(Neighbors neighbors) 
    {
        this.neighbors = neighbors;
    }

    public boolean equals(Coordinate other) 
    {
        return this.x == other.getCoordinateX() && this.y == other.getCoordinateY();
    }

    public boolean equals(int x, int y) 
    {
        return this.x == x && this.y == y;
    }

    @Override
    public String toString() 
    {
        return "(" + this.x + ", " + this.y + ")";
    }
}

enum Directions
{
    LEFT, RIGHT, UP, DOWN;

    public Directions opposite() 
    {
        switch (this) {
                
            case LEFT:
                return RIGHT;
                
            case RIGHT:
                return LEFT;
                
            case UP:
                return DOWN;
                
            case DOWN:
                return UP;
                
            default:
                return null;
        }
    }
}

class Node<Template> 
{
    private Template data;
    private Node<Template> next;

    public Node(Template data) 
    {
        this.data = data;
        this.next = null;
    }

    public Template getData() 
    {
        return this.data;
    }

    public void setData(Template data) 
    {
        this.data = data;
    }

    public Node<Template> getNext() 
    {
        return this.next;
    }

    public void setNext(Node<Template> next) 
    {
        this.next = next;
    }
}

class Players implements ComparableObject 
{
    private String name;
    private Directions[] directions = new Directions[4];
    private int[][] stepsMap;
    private int steps;

    public Players(String name, String[] directions) 
    {
        this.name = name;
        this.steps = 0;
        for (int i = 0; i < directions.length; i++) 
        {
            this.directions[i] = Directions.valueOf(directions[i]);
        }
    }

    public Players(String rawString) 
    {
        String[] split = rawString.split(" ");
        this.name = split[0];
        this.steps = 0;
        for (int i = 0; i < 4; i++) 
        {
            this.directions[i] = Directions.valueOf(split[i + 1]);
        }
    }

    public String getName() 
    {
        return name;
    }

    public Directions[] getDirections() 
    {
        return directions;
    }

    public Board.Step getStepTracks() 
    {
        return new Board.Step(this.stepsMap);
    }

    public int getSteps() 
    {
        return steps;
    }

    public void traverseMap(Board board) 
    {
        this.stepsMap = new int[board.getSizeX()][board.getSizeY()];
        if (!traverseMaze(board.getStart(), board, null)) 
        {
            this.steps = -1;
        }
    }

    
    public boolean traverseMaze(Coordinate current, Board maze, Directions lastDirection) 
    {
        if (current == null)
        {
            return false;
        }
        if (stepsMap[current.getCoordinateX()][current.getCoordinateY()] != 0)
        {
            return false;
        }

        stepsMap[current.getCoordinateX()][current.getCoordinateY()] = ++steps;
        if (maze.isGoal(current))
        {
            return true;
        }
        Coordinate next = null;
        Coordinate.Neighbors neighbors = current.getNeighbors();

        for (Directions direction : directions) 
        {
            if (lastDirection == direction.opposite())
            {
                continue;
            }
            switch (direction) 
            {
                case UP:
                    next = neighbors.getUpSide();
                    break;
                case DOWN:
                    next = neighbors.getDownSide();
                    break;
                case LEFT:
                    next = neighbors.getLeftSide();
                    break;
                case RIGHT:
                    next = neighbors.getRightSide();
                    break;
            }
            if (traverseMaze(next, maze, direction)) 
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) 
    {
        Players other = (Players) o;
        if (this.steps > other.getSteps())
        {
            return 1;
        }
        else if (this.steps < other.getSteps())
        {
            return -1;
        }
        else 
        {
            return this.name.compareTo(other.getName());
        }
    }
}

class SortedList<T extends ComparableObject>
{
    private Node<T> head;
    private int size;

    public SortedList() 
    {
        this.head = null;

        this.size = 0;
    }

    public void add(T data) 
    {
        Node<T> node = new Node<T>(data);
        if (this.head == null) 
        {
            this.head = node;

        } else 
        {
            Node<T> current = this.head;
            Node<T> previous = null;

            while (current != null) 
            {
                if (current.getData().compareTo(data) > 0) 
                {
                    break;
                }
                previous = current;
                current = current.getNext();
            }
            if (previous == null) 
            {
                node.setNext(this.head);
                this.head = node;
            } else 
            {
                node.setNext(current);
                previous.setNext(node);
            }
        }
        this.size++;
    }

    public T remove() 
    {
        if (this.head == null) 
        {
            return null;
        } else 
        {
            Node<T> nodes = this.head;
            this.head = this.head.getNext();
            this.size--;
            return nodes.getData();
        }
    }

    public int size() 
    {
        return this.size;
    }

    public boolean isEmpty() 
    {
        return this.size == 0;
    }

    public Node<T> getHead() 
    {
        return this.head;
    }
}

class Queue<T> 
{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Queue() 
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void EnQueue(T data) 
    {
        Node<T> node = new Node<T>(data);
        if (this.head == null) 
        {
            this.head = node;
            this.tail = node;
        } else 
        {
            this.tail.setNext(node);
            this.tail = node;
        }
        this.size++;
    }

    public T DeQueue() 
    {
        if (this.head == null) 
        {
            return null;
        } 
        else 
        {
            Node<T> node = this.head;
            this.head = this.head.getNext();
            this.size--;
            return node.getData();
        }
    }

    public int size() 
    {
        return this.size;
    }

    public boolean isEmpty() 
    {
        return this.size == 0;
    }
}



public class Main 
{
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        int square = Integer.parseInt(input.nextLine());

        String road = input.nextLine();

        int playerCounter = Integer.parseInt(input.nextLine());

        Queue<Players> queue = new Queue<Players>();
        
        for (int i = 0; i < playerCounter; i++) 
        {
            queue.EnQueue(new Players(input.nextLine()));
        }

        input.close();

        System.out.println("");
        
        Board board = new Board(square);
        
        board.addRoad(road.split(" "));

        SortedList<Players> leaderboard = new SortedList<Players>();

        while (!queue.isEmpty()) 
        {
            Players player = queue.DeQueue();
            
            player.traverseMap(board);
            
            leaderboard.add(player);
        }

        Players player;
        
        Node<Players> current;


        current = leaderboard.getHead();
        
        while (current != null) 
        {
            player = current.getData();
            
            current = current.getNext();
            
            System.out.println(player.getName());
            
            board.generateBoard(player.getStepTracks());
        }

        current = leaderboard.getHead();

        if (current != null) 
        {
            player = current.getData();
            
            System.out.print("\nWINNER ");
            System.out.print(String.format("%s %d steps", player.getName(), player.getSteps()));
            
            current = current.getNext();
        }
        while (current != null) 
        {
            player = current.getData();
            
            System.out.print(String.format("\n%s %d steps", player.getName(), player.getSteps()));
            
            current = current.getNext();
        }
        System.out.println("\n");
    }
}