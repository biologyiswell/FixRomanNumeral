package io.github.biologyiswell.frn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestFRNUtil { // package-private

	@Test
	void run() {
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(1), "I");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(10), "X");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(50), "L");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(100), "C");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(500), "D");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(1000), "M");

		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(2), "II");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(11), "XI");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(51), "LI");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(101), "CI");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(501), "DI");
		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(1001), "MI");

		Assertions.assertEquals(FRNUtil.translateToRomanNumeral(4981), "MMMMDCCCCLXXXI");
	}
}