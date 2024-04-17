package firsttry.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private String username;
    private String firstname;
    private String lastname;

    // Add other necessary fields based on your requirements
}
