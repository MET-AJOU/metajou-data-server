package com.minshigee.dataserver.domain.model3d;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/model3d")
@RequiredArgsConstructor
public class Model3DController {

    private final Model3DService model3DService;



}
