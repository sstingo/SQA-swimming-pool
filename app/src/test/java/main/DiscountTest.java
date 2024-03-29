package main;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DiscountTest {

	@DisplayName("不同年紀的折扣")
	@Nested
	class DifferentAges {
		String dateTime = "2021-05-26 週三 14:30:00";

		@Test
		void testAgeLessThan() throws Throwable {
			Identity identity = new Identity(2, false, true);
			try {
				new Discount(identity, dateTime);
			} catch (Throwable exception) {
				Assertions.assertEquals("Your age is too young.", exception.getMessage());
			}
		}

		@Test
		void testAgeHasDiscount01() throws Throwable {
			Identity identity = new Identity(10, false, false);
			Discount discount = new Discount(identity, dateTime);
			Assertions.assertEquals(0.8, discount.getDiscount());
		}

		@Test
		void testAgeHasDiscount02() throws Throwable {
			Identity identity = new Identity(65, false, false);
			Discount discount = new Discount(identity, dateTime);
			Assertions.assertEquals(0.8, discount.getDiscount());
		}

		@Test
		void testAgeMoreThan() throws Throwable {
			Identity identity = new Identity(76, false, true);
			try {
				new Discount(identity, dateTime);
			} catch (Throwable exception) {
				Assertions.assertEquals("Your age doesn't meet the requirements.", exception.getMessage());
			}
		}
	}

	@DisplayName("營業與非營業時間")
	@Nested
	class DifferentBusinessHours {
		// @Test
		// void testBeforeBusiness() throws Throwable {
		// 	Identity identity = new Identity(25, false, false);
		// 	try {
		// 		new Discount(identity, "2021-05-26 週三 04:30:00");
		// 	} catch (Throwable exception) {
		// 		Assertions.assertEquals("Business hours: 05:00-22:00", exception.getMessage());
		// 	}
		// }

		@Test
		void testEarlyBirdBusiness01() throws Throwable {
			Identity identity = new Identity(25, false, false);
			Discount discount = new Discount(identity, "2021-05-26 週三 05:00:00");
			Assertions.assertEquals(0.8, discount.getDiscount());
		}

		@Test
		void testEarlyBirdBusiness02() throws Throwable {
			Identity identity = new Identity(25, false, false);
			Discount discount = new Discount(identity, "2021-05-26 週三 06:30:00");
			Assertions.assertEquals(0.8, discount.getDiscount());
		}

		@Test
		void testHaveBusiness01() throws Throwable {
			Identity identity = new Identity(25, false, false);
			Discount discount = new Discount(identity, "2021-05-26 週三 10:30:00");
			Assertions.assertEquals(1, discount.getDiscount());
		}

		@Test
		void testHaveBusiness02() throws Throwable {
			Identity identity = new Identity(25, false, false);
			Discount discount = new Discount(identity, "2021-05-26 週三 22:00:00");
			Assertions.assertEquals(1, discount.getDiscount());
		}

		// @Test
		// void testOverBusiness01() throws Throwable {
		// 	Identity identity = new Identity(25, false, false);
		// 	try {
		// 		new Discount(identity, "2021-05-26 週三 23:30:00");
		// 	} catch (Throwable exception) {
		// 		Assertions.assertEquals("Business hours: 05:00-22:00", exception.getMessage());
		// 	}
		// }

		// @Test
		// void testOverBusiness02() throws Throwable {
		// 	Identity identity = new Identity(25, false, false);
		// 	try {
		// 		new Discount(identity, "2021-05-26 週三 22:30:00");
		// 	} catch (Throwable exception) {
		// 		Assertions.assertEquals("Business hours: 05:00-22:00", exception.getMessage());
		// 	}
		// }

		@ParameterizedTest
	    @ValueSource(strings = { 
	    	"2021-05-26 週三 04:30:00", 
	    	"2021-05-26 週三 22:30:00", 
	    	"2021-05-26 週三 23:30:00", 
	    })
	    void testHaveNotBusiness(String dateTime) throws Throwable {
	        Identity identity = new Identity(25, false, false);
	        try {
				new Discount(identity, dateTime);
			} catch (Throwable exception) {
				Assertions.assertEquals("Business hours: 05:00-22:00", exception.getMessage());
			}
    	}
	}

	@DisplayName("團體/會員")
	@Nested
	class DifferentIdentities {
		String dateTime = "2021-05-26 週三 14:30:00";

		@Test
		void testGroupHasDiscount() throws Throwable {
			Identity identity = new Identity(25, false, true);
			Discount discount = new Discount(identity, dateTime);
			Assertions.assertEquals(0.7, discount.getDiscount());
		}

		@Test
		void testMemberHasDiscount() throws Throwable {
			Identity identity = new Identity(25, true, false);
			Discount discount = new Discount(identity, dateTime);
			Assertions.assertEquals(0.5, discount.getDiscount());
		}

		@Test
		void testGroupMemberHasDiscount() throws Throwable {
			Identity identity = new Identity(25, true, true);
			Discount discount = new Discount(identity, dateTime);
			Assertions.assertEquals(0.5, discount.getDiscount());
		}
	}
}
