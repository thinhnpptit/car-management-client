package btl;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force=true)
public class Car {
	private Long id;
	@NotBlank(message="Brand name is required") 
	private String brandname;
	@NotBlank(message="Plate number is required") 
	private String platenumber;
	@NotBlank(message="Color is required") 
	private String color;
	@NotBlank(message="Model is required") 
	private String model;
	@Digits(integer=3, fraction=0, message="Invalid") 
	private int seatnumber;
	@Digits(integer=1, fraction=1, message="Invalid") 
	private int usedyear;
	@Pattern(regexp="^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message="Must be formatted yyyy-MM-dd") 
	private String lastmaintaincedate;
}
