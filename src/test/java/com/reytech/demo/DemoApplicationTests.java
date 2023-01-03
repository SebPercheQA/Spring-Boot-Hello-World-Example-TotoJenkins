// package com.reytech.demo;
// import static org.junit.jupiter.api.Assertions.assertAll;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import com.reytech.demo.Personne;

// @SpringBootTest
// class DemoApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}
	
// 	@Test
//         void groupedAssertions() {
//         Personne pers = new Personne("Nassur", "Moumadi");

//         assertAll("personne", () -> assertEquals("Nassur", pers.getFirstName()),
//                               () -> assertEquals("Moumadi", pers.getLastName()));
//     }
// }

package com.reytech.demo;

public class Personne {

    public String firstName;
    public String lastName;

    public Personne(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
