package com.tuvn.webNA.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionsUtils {
	public static HttpSession getSession()
    {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest()
    {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    //Metodo para obtener nombre de usuario
    public static String getUserName(){
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        return session.getAttribute("username").toString();
    }

    //Metodo par obtener el ID del Usuaio
    public static String getUserId()
    {
        HttpSession session = getSession();
        if(session != null){
            return (String) session.getAttribute("userId");
        }else{
            return null;
        }
    }
}
