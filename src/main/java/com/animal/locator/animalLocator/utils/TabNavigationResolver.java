package com.animal.locator.animalLocator.utils;

import org.thymeleaf.util.StringUtils;

public class TabNavigationResolver {
    public static int resolveActive(final String tab)
    {
        int result = 1;
        if (!StringUtils.isEmpty(tab))
        {
            switch (tab)
            {
                case "basic_information":
                case "active_users":
                    result = 1;
                    break;
                case "safe_zone":
                case "on_hold_users":
                    result = 2;
                    break;
                case "treatments":
                case "not_active_users":
                    result = 3;
                    break;
                case "statistics":
                case "create_animal":
                    result = 4;
                    break;
                default:
                    result = 1;
                    break;
            }
        }
        return result;
    }
}
