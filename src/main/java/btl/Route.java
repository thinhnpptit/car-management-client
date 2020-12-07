package btl;


import lombok.Data;

@Data
public class Route {
	private long id;
	private String start;
	private String end;
	private float length;
	private int complicatedlevel;
}
