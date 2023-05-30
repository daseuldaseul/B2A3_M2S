package B2A3_M2S.mes.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class CommonCodeControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    void list() throws Exception {
//        log.info("list 테스트" +
//                mockMvc.perform(MockMvcRequestBuilders.get("/system/list")
//                                .param("useYn", "Y"))
//                        .andDo(print())
//                        .andExpect(view().name("system/list")).andReturn()
//                        .getModelAndView()
//                        .getModel()
//                        .get("group"));
//    }
//
//    @Test
//    void detail() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/system/detail")
//                .param("codeGroup", "ITEM_GB")
//                .param("useYn", "Y"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}