package com.yestae.utils;

import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhangzhi
 * @description: TODO
 * @Date 2019/8/2 9:50
 **/
public class CommonUtils {

    public static void shardingString(Map<Integer, Object> map, int nums, String param, String splitPoint, String otherPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!param.contains(",") && !param.contains(";")) {
            map.put(0, param);
        }
        String[] split = param.split(splitPoint);
        int totalSize = split.length;
        int totalPage = totalSize / nums;
        if (totalSize % nums != 0) {
            totalPage += 1;
            if (totalSize < nums) {
                nums = totalSize;
            }
        }

        for (int i = 1; i < totalPage + 1; i++) {
            int starNum = (i - 1) * nums;
            int endNum = i * nums > totalSize ? (totalSize) : i * nums;
            List<String> strings = Lists.newArrayList(split).subList(starNum, endNum);
            strings.forEach((s) -> {
                if (StringUtils.isEmpty(otherPoint)) {
                    stringBuilder.append(s).append(splitPoint);
                } else {
                    stringBuilder.append(s).append(otherPoint);
                }

            });
            map.put(i, stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
        }
    }
}
