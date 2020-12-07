package btl;

import java.sql.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class Trip {
	private long id;
	@NotBlank(message = "Driver Id is required")
	private Employee driver;
	@NotBlank(message = "Subdriver Id is required")
	private Employee subdriver;
	@NotBlank(message = "Car Id is required")
	private Car car;
	@NotBlank(message = "Route Id is required")
	private Route route;
	@Digits(integer = 3, fraction = 0, message = "Invalid")
	private int numberofpassengers;
	private float price;
	@Digits(integer = 3, fraction = 1, message = "Invalid")
	private Date createdat;
}