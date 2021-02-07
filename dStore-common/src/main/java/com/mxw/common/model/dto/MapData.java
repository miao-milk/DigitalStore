package com.mxw.common.model.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class MapData {

    private List<HashMap<String, String> > mapData;
}
