package ca.nait.dmit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BMI {
    private double weight;
    private double height;

    public double bmi() {
        return 703 * weight / Math.pow(height,2);
    }

    public String bmiCategory() {
        if (bmi() <= 18.5){
            return "Underweight";
        }else if(bmi() >= 18.5 && bmi() <= 24.9){
            return "Normal";
        }else if(bmi() >= 25 && bmi() <= 29.9){
            return "Overweight";
        }else{
            return "Obese";
        }
    }
}
