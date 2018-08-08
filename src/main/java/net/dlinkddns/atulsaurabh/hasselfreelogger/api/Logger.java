/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.dlinkddns.atulsaurabh.hasselfreelogger.api;

import java.util.Properties;

/**
 *
 * @author Atul Saurabh
 * @version 1.0
 */
public interface Logger 
{
  /**
   * 
   * @param message Fatal message to be logged
   */
   public void logFatal(String message);
   /**
    * 
    * @param errorClass Class where exception is caught
    * @param message Fatal message to be logged
    */
   public void logFatal(Class errorClass,String message);
   /**
    * 
    * @param errorClass Class where exception is caught
    * @param message    Fatal message to be logged
    * @param throwable  The instance of exception generated from stack trace
    */
   public void logFatal(Class errorClass,String message,Throwable throwable);
   
   /**
    * 
    * @param message Any message to be logged in 
    */
   public void logAll(String message);
   /**
    * 
    * @param errorClass Any error or warning or info etc generating class
    * @param message  Any message to be logged in
    */
   public void logAll(Class errorClass,String message);
   /**
    * 
    * @param errorClass Any error or warning or info etc generating class
    * @param message Any message to be logged in
    * @param throwable Any object representing warning,error,fatal etc.
    */
   public void logAll(Class errorClass,String message,Throwable throwable);
   
   /**
    * 
    * @param rollingon make rolling on so that log can be generated and stored date wise 
    */
   public void setRollingOn(boolean rollingon);
   /**
    * 
    * @return the state of rolling 
    */
   public boolean isRollingOn();
   /**
    * 
    * @param pattern The pattern for date used in log file 
    */
   public void setDatePattern(String pattern);
   /**
    * 
    * @return date format used in log file 
    */
   public String getDatePattern();
   /**
    * 
    * @param pattern Format for printing message into log file 
    */
   public void setConversionPattern(String pattern);
   /**
    * 
    * @return Format for printing message into log file 
    */
   public String getConversionPattern();
   
   
   public void logWarning(String message);
   public void logWarning(Class warningClass,String message);
   public void logWarning(Class warningClass,String message,Throwable throwable);
   
   public void logInfo(String message);
   public void logInfo(Class infoClass,String message);
   public void logInfo(Class infoClass,String message,Throwable throwable);
   
   public void logDebug(String message);
   public void logDebug(Class debugClass,String message);
   public void logDebug(Class debugClass,String message,Throwable throwable);
   
   public void logError(String message);
   public void logError(Class errorClass,String message);
   public void logError(Class errorClass,String message,Throwable throwable);
   
    public void setConfiguration(Properties properties);
}
