package de.footystats.tools.services.prediction.heatmap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface HeatMapped {

	// Only relevant for Float and Double fields. The fraction of the value that is used for the heatmap calculation.
	// e.G. 0 means that no fraction digit is used to look for existing values.
	// e.G. 1 means that one fraction digit is used to look for existing values.
	// 2 is the default value because footystats csv files use 2 fraction digits for float values.
	int fraction() default 2;

	// The name of the property that is used for the heatmap calculation. Should be stable over the whole lifetime of the application.
	// Used to find existing values for the heatmap calculation. Renaming this property after values have been stored will lead
	// to a new heatmap calculation for this property. All other calculations for this property (based on the old value) will not be used anymore.
	String heatMappedProperty();
}
