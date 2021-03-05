package nc.unc.cs.services.communal.controllers.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(ObjectMapper.class)
public class ParentWeb {
  @Autowired protected MockMvc mockMvc;
  @Autowired protected ObjectMapper objectMapper;
}
