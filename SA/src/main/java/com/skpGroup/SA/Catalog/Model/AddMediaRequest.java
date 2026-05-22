package com.skpGroup.SA.Catalog.Model;

import java.util.List;

public record AddMediaRequest(

        List<String> image_urls,

        List<String> video_urls

) {}