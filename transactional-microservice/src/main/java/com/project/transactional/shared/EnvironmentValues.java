package com.project.transactional.shared;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EnvironmentValues {

    @Value("${server.transactional-domain}")
    String domain;
}
