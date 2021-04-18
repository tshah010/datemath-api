package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class DateMathControllerTest {
    @InjectMocks
    DateMathController dateMathController;

    @Test
    public void testCalculate()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<?> responseEntity = dateMathController.calculate("24", "1", "1", "12-03-2001");

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(((Result) Objects.requireNonNull(responseEntity.getBody())).getAnswer().equals("12-27-2001"));

    }
}
