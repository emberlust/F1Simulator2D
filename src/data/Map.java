package data;

public class Map {
	private float[][][] map_matrix;
	private int map_size;
	private Coordinates start_line;
	
	public Map()
	{
		
	}
	
	public void init_map(int size)
	{
		this.map_size=size;
		this.map_matrix = new float[size][size][2]; 
		
		for(int i = 0; i < size ; i++)
		{
			for(int j = 0; j < size ; j++)
			{
				this.map_matrix[i][j][0] = 0;
				this.map_matrix[i][j][1] = Float.MAX_VALUE;
			}
		}
	}
	
	
	//1 road tile
	//2 box tile
	
	public Coordinates generate_simple_path()
	{	
		
		int i,j;
		for(j=2;j<this.map_size-3;j++)
		{
			this.map_matrix[2][j][0]=1;
		}
		this.map_matrix[2][j][1]=50;
		
		for(i=2;i<this.map_size-3;i++)
		{
			this.map_matrix[i][this.map_size-3][0]=1;
		}
		this.map_matrix[i][this.map_size-4][1]=50;
		
		for(j=this.map_size-3;j>2;j--)
		{
			this.map_matrix[this.map_size-3][j][0]=1;
		}
		this.map_matrix[this.map_size-4][j][1]=50;
		
		for(i=this.map_size-3;i>2;i--)
		{
			this.map_matrix[i][2][0]=1;
		}
		this.map_matrix[i][2][1]=50;
		
		this.map_matrix[3][this.map_size/2][0]=2;
		this.map_matrix[this.map_size-4][this.map_size/2][0]=2;
		
		this.start_line = new Coordinates();
		start_line.y=this.map_size/2;
		start_line.x=2;
		
		return start_line;
		
	}
	
	public Coordinates get_start_line()
	{
		return this.start_line;
	}
	
	public float map_data(int x, int y, int z)
	{
		return this.map_matrix[x][y][z];
	}
	
	public void show_map_matrix()
	{
		for(int i=0;i<this.map_size;i++)
		{
			for(int j=0;j<this.map_size;j++)
			{
				System.out.print(this.map_matrix[i][j][0] + " ");
			}
			System.out.println();
		}
	}
	
}
