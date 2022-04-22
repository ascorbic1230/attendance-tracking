package org.example.utils;

import javax.swing.*;
import java.awt.*;

public class AppUtil {
    private static final String logoImgPath = "./assets/images/logo.png";
    private static final String appNameVn = "Phần mềm điểm danh";

    public static Image getAppLogo() {
        return new ImageIcon(logoImgPath).getImage();
    }

    public static String getAppNameVn() {
        return appNameVn;
    }
}
