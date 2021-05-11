package com.arihant.datemathapi.service;

import com.arihant.datemathapi.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
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

        Map<String, String> headers = new HashMap<>();
        headers.put("accept-language", "en-US");
        headers.put("Referer", "http://unittest:3000/");

        ResponseEntity<?> responseEntity = dateMathController.calculate(24, 2, 1, "12-03-2001 00:00", headers);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(((Result) Objects.requireNonNull(responseEntity.getBody())).getAnswer()).isEqualTo("Thu, 27 Dec 2001 00:00 PST");

    }

    @Test
    public void testCalculateDateDifference()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept-language", "en-US");
        headers.put("Referer", "http://unittest:3000/");

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<?> responseEntity = dateMathController.calculateDateDifference("12-02-2014 11:00", "12-02-2014 7:15", headers);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(((Result) Objects.requireNonNull(responseEntity.getBody())).getAnswer()).isEqualTo("0 days, 3 hours, 45 minutes");

    }
}
