package red.stevo.code.springsecurity.AppModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private  final String ROLE1 = "ADMIN";

    private  final  String ROLE2 = "USER";
}
