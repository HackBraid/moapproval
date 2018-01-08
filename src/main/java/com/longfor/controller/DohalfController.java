package com.longfor.controller;

import com.longfor.service.DohalfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dohalfapi")
public class DohalfController {
    private final static Logger logger= LoggerFactory.getLogger(FlowController.class);

    @Autowired
    DohalfService dohalfService;


}
