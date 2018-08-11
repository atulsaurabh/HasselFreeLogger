/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.dlinkddns.atulsaurabh.hasselfreelogger.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import net.dlinkddns.atulsaurabh.hasselfreelogger.api.Logger;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;



/**
 *<h3>Dependencies</h3>
 * This implementation is depending upon <a href="https://logging.apache.org/log4j/">Log4j</a>. 
 * To use this class in <a href="https://maven.apache.org/">Maven</a> project following
 * dependency need to added in the pom file.
 * <pre>
 * {@code 
 *        <dependency>
 *        <groupId>log4j</groupId>
 *        <artifactId>log4j</artifactId>
 *        <version>1.2.17</version>
 *        </dependency>
 * }
 * </pre>
 * Otherwise Log4j dependencies need to be satisfied manually.
 * <h3>Purposes</h3>
 * <p>
 * Purpose of this class is to provide a hassle free logging technique. 
 * The implementation supports rolling based logging as well as non rolling
 * based logging mechanism. The rolling option can be activated by setting 
 * {@link #rollingOn} field.
 * 
 *<p>
 * Using this class following level of log can be recorded
 * <pre>
 * {@link org.apache.log4j.Level#ALL}, {@link org.apache.log4j.Level#DEBUG},
 * {@link org.apache.log4j.Level#ERROR}, {@link org.apache.log4j.Level#FATAL},
 * {@link org.apache.log4j.Level#INFO} and {@link org.apache.log4j.Level#WARN}
 * </pre>
 * 
 *<h3>Configuration</h3>
 * <p>
 * On the basis of above mentioned {@link org.apache.log4j.Level} and if the rolling 
 * is active then different type of log is recorded in different files. These files 
 * can be configured by changing inside {@link ResourceBundle} file.
 * The following keys are used to configure the log files:
 * <ul>
 *     <li> log.all         : To log {@link org.apache.log4j.Level#ALL}
 *     <li> log.debug       : To log {@link org.apache.log4j.Level#DEBUG} 
 *     <li> log.error       : To log {@link org.apache.log4j.Level#ERROR}
 *     <li> log.fatal       : To log {@link org.apache.log4j.Level#FATAL}
 *     <li> log.info        : To log {@link org.apache.log4j.Level#INFO}
 *     <li> log.warn        : To log {@link org.apache.log4j.Level#WARN}
 *     <li> log.datepattern : To configure the pattern of date of logging
 *     <li> log.rolling     : To set the rolling on or off
 * </ul>
 * Along with the files, the director for log repository can also be configured
 * by setting the key <b><i>log.directory</i></b> in {@link Properties} file.
 * <h3>Features</h3>
 * <p>
 * The rolling based logging mechanism provides a way to log the message on the 
 * basis of date and time. The date and time option is configurable.
 * The format can be decided using {@link #setDatePattern(java.lang.String)} method. 
 * <p>
 * The format of content is also configurable. This format can be modified using
 * {@link #setConversionPattern(java.lang.String)} method.
 * 
 * @see #setRollingOn(boolean)
 * @see #setDatePattern(java.lang.String) 
 * @see #setConversionPattern(java.lang.String) 
 * 
 *@version 1.0
 *@author Atul Saurabh
 *@since 1.0  
 */

public class HasselFreeLogger implements Logger
{
  
    /**
     * This field stores the directory where the log file will be generated.
     * @since 1.0
     * 
     */
   private String log_directory_name;
   
   /**
    * This field stores the name of the log file where {@link Level#FATAL} is recorded.
    * @since 1.0
    */
   private String fatal_log_file_name;
   
  /**
    * This field stores the name of the log file where {@link Level#DEBUG} is recorded.
    * @since 1.0
    */
   private String debug_log_file_name;
   
   /**
    * This field stores the name of the log file where {@link Level#INFO} is recorded.
    * @since 1.0
    */
   private String info_log_file_name;
   
   /**
    * This field stores the name of the log file where {@link Level#ERROR} is recorded.
    * @since 1.0
    */
   private String error_log_file_name;
   
   /**
    * This field stores the name of the log file where {@link Level#WARN} is recorded.
    * @since 1.0
    */
   private String warn_log_file_name;
   
   /**
    * This field stores the name of the log file where {@link Level#ALL} is recorded.
    * @since 1.0
    */
   private String all_log_file_name;
   
   /**
    * This field stores the pattern used to record the log message.
    * @since 1.0
    */
   
   private String conversionPattern;
   
   /**
   * The field is required to make rolling on feature on or off.
   * @since 1.0
   */
   private boolean rollingOn=false;
  
   /**
    * This field stores pattern used in recording the log.
    * @since 1.0
    */
   private String datePattern;
   
   /**
    * This is a default constructor. It provides the default configuration for the 
    * system. By default the rolling feature is kept off and date pattern is
    * dd-MM-yyyy e.g. 03-05-2018.
    * Some default file names and directory name is also provided.
    * @since 1.0
    */
    public HasselFreeLogger() 
    {
        this.log_directory_name="log";
        this.all_log_file_name="all.log";
        this.debug_log_file_name="debug.log";
        this.error_log_file_name="error.log";
        this.info_log_file_name="info.log";
        this.warn_log_file_name="warn.log";
        this.fatal_log_file_name="fatal.log";
        this.datePattern="dd-MM-yyyy";
        this.rollingOn=false;
    }

    /**
     * 
     * @param properties The parameter is required if the external configuration is 
     *                   required. All the external configuration should be stored
     *                   inside a {@link  java.util.Properties} file.  Inside properties
     *                   file, the information is stored in form of <b>key=value</b>
     *                   pair. The keys are already decided. The value may be any user defined
     *                   value. The system consider project root as the root directory. So all
     *                   the values must be relative to the project root only.
     * @since 1.0
     */
    public HasselFreeLogger(Properties properties) {
        setOptions(properties);
    }
    
    
    /**
     * This is a {@literal private} method. This method reads all the external 
     * configuration and stores inside corresponding fields. This method is called 
     * from two major points viz {@link #HasselFreeLogger(java.util.Properties)} and
     * {@link #setConfiguration(java.util.Properties)}.
     * This means that user has two choices:
     * <ul>
     * <li> Create the instance using {@link #HasselFreeLogger() } and override settings
     *      using {@link #setConfiguration(java.util.Properties) }
     * <li> Create instance using {@link #HasselFreeLogger(java.util.Properties) }
     * </ul>
     * @param properties used for external configuration.
     * @since 1.0
     */
    private void setOptions(Properties properties)
    {
        this.log_directory_name=properties.getProperty("log.directory");      
         this.fatal_log_file_name = properties.getProperty("log.fatal");
         this.debug_log_file_name= properties.getProperty("log.debug");
         this.error_log_file_name=properties.getProperty("log.error");
         this.info_log_file_name=properties.getProperty("log.info");
         this.warn_log_file_name=properties.getProperty("log.warn");
         this.all_log_file_name=properties.getProperty("log.all");
         this.datePattern=properties.getProperty("log.datepattern");
         this.rollingOn=Boolean.valueOf(properties.getProperty("log.rolling"));
    }
    
    
    /**
     * If the user has created an instance using {@link #HasselFreeLogger() } constructor 
     * and still want to use external configuration, it can use this method to override 
     * the settings.
     * @param properties externally configured information. 
     * @since 1.0
     */

    public void setConfiguration(Properties properties) 
    {
        setOptions(properties);
    }
   
    /**
     * Records the fatal level log message. 
     * @param message The fatal level message to be logged in.
     * @since 1.0 
     */
    @Override
    public void logFatal(String message)
    {
      org.apache.log4j.Logger logger=log(null, Level.FATAL);
      logger.fatal(message);
    }

    /**
     * 
     * @param errorClass  The class where the exception is generated.
     * @param message The fatal level message to be logged in.
     * @since 1.0
     */
    @Override
    public void logFatal(Class errorClass, String message) {
        org.apache.log4j.Logger logger=log(errorClass, Level.FATAL);
        logger.fatal(message);
    }
    
    /**
     * 
     * @param errorClass The class where the exception is generated.
     * @param message The fatal level message to be logged in.
     * @param throwable The exception to be logged in.
     * @since 1.0
     */

    @Override
    public void logFatal(Class errorClass, String message, Throwable throwable) {
      org.apache.log4j.Logger logger=log(errorClass, Level.FATAL);
        logger.fatal(message,throwable);  
    }
    
    /**
     * 
     * @param message The message to be logged in. The logging will be done for 
     *                every level.
     * @since 1.0
     */

    @Override
    public void logAll(String message) {
      org.apache.log4j.Logger logger=log(null, Level.ALL);
      logger.fatal(message);
    }
    
    
    /**
     * 
     * @param errorClass The class where exception is caught
     * @param message The message to be logged in. The logging will be done for 
     *                every level.
     * @since 1.0
     */

    @Override
    public void logAll(Class errorClass, String message) {
        org.apache.log4j.Logger logger=log(errorClass, Level.ALL);
        logger.fatal(message);
    }

    
    /**
     * 
     * @param errorClass The class where the exception is caught
     * @param message The message to be logged in. The logging will be done for 
     *                every level.
     * @param throwable The exception generated.
     * @since 1.0
     */
    @Override
    public void logAll(Class errorClass, String message, Throwable throwable) {
        org.apache.log4j.Logger logger=log(errorClass, Level.ALL);
        logger.fatal(message,throwable);
    }
    
    
    /*
        I believe that all kind of log method requires level to decide 
        what kind of file need to be generated and what format to use while 
        recording the log. So this decision can be made centrally. That is why I
        designed this central method to produce a logger object with the decision of
        a) The level of the log
        b) which class is generating the log
        
        That is why this method requies two parameters
        a) errorClass : where the exception is generated
        b) level of the log.
    */
    private org.apache.log4j.Logger log(Class errorClass,Level level)
    {
        /*
            If the user of the system do not want to supply error generating class
            then by default the current class will be considered to be the class which is 
             generating the eeror.
        */
        org.apache.log4j.Logger logger=null;
        if(errorClass == null)
            logger= org.apache.log4j.Logger.getLogger(this.getClass().getName());
        else
            /*
               
            */
            logger=org.apache.log4j.Logger.getLogger(errorClass.getName());
         /*
            If the user want to record the log into respective file then the rollingOn
            should be true.
        */   
      if(isRollingOn())
      {
          try {
              /*
                 I want to record different level of log into different files.
                  So what file wiil be used will depened upon the level.
              */
              String fileName="default.log";
              if(level == Level.ALL)
                  fileName = all_log_file_name;
              else if(level == Level.DEBUG)
                  fileName = debug_log_file_name;
              else if(level == Level.ERROR)
                  fileName = error_log_file_name;
              else if(level == Level.FATAL)
                  fileName = fatal_log_file_name;
              else if(level == Level.INFO)
                  fileName = info_log_file_name;
              else if(level == Level.WARN)
                  fileName = warn_log_file_name;
              
              logger.addAppender(getRollingFileAdapter(fileName));
          } catch (URISyntaxException e) {
             java.util.logging.Logger.getLogger(this.getClass().getName()).log(java.util.logging.Level.SEVERE, 
                     "URL Is Not Valid For Log File\nUnable To Create Daily LOG FILE");
             logger.addAppender(new ConsoleAppender());
          }    
      }
      logger.setLevel(level);
      return logger;
    }
   /**
    * @see org.apache.log4j.DailyRollingFileAppender
    * @return DailyRollingFileAppender. This class is used for creating date wise log.
    * @throws URISyntaxException throws exception if the URI for log directory is not valid
    * @param fileName File name where the log will be recorded.
    * @since 1.0
    */
  private DailyRollingFileAppender getRollingFileAdapter(String fileName) throws URISyntaxException
  {
      URI logDIRFullName = this.getClass().getResource("/").toURI();
      String normalizePath = Paths.get(logDIRFullName).toString();

      String log_file_full_name = normalizePath + "/" + log_directory_name + "/" + fileName;
      PatternLayout layout = new PatternLayout();
      String conversionPattern = getConversionPattern();
      layout.setConversionPattern(getConversionPattern());
      DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
      rollingAppender.setFile(log_file_full_name);
      rollingAppender.setDatePattern("'.'"+getDatePattern());
      rollingAppender.setLayout(layout);
      rollingAppender.activateOptions();
      return rollingAppender;
  }

    /**
     * 
     * @return the conversion pattern i.e. the pattern used to log the message
     * @since 1.0
     */
    @Override
    public String getConversionPattern()
    {
        if(conversionPattern == null)
            return "[%p] %d %c %M - %m%n";
        else
            return conversionPattern;
    }
    
    /**
     * 
     * @param conversionPattern This a {@link java.lang.String} which represents
     * the patterns used to record the log messages.
     * @since 1.0
     */

    @Override
    public void setConversionPattern(String conversionPattern) {
        this.conversionPattern = conversionPattern;
    }

    /**
     * 
     * @param message The message to be recorded in. This is a warning message.
     * @since 1.0
     */
    @Override
    public void logWarning(String message) {
        org.apache.log4j.Logger logger = log(null, Level.WARN);
        logger.warn(message);
    }
    
    /**
     * 
     * @param warningClass The {@link java.lang.Class} where a warning message is generated.
     * @param message The message to be recorded in.
     * @see java.lang.Class
     * @since 1.0
     */

    @Override
    public void logWarning(Class warningClass, String message) {
      org.apache.log4j.Logger logger = log(warningClass, Level.WARN);
        logger.warn(message);  
    }
    
    /**
     * 
     * @param warningClass The {@link java.lang.Class} generating the warning message.
     * @param message The message to be recorded in.
     * @param throwable The exception generated.
     * @see java.lang.Class
     * @see java.lang.Throwable
     * @since 1.0
     */

    @Override
    public void logWarning(Class warningClass, String message, Throwable throwable) {
       org.apache.log4j.Logger logger = log(warningClass, Level.WARN);
       logger.warn(message,throwable); 
    }
    
    /**
     * 
     * @param message The information message to be recorded in.
     * @since 1.0
     */

    @Override
    public void logInfo(String message) {
      org.apache.log4j.Logger logger = log(null, Level.INFO);
       logger.info(message);   
    }
    
    /**
     * 
     * @param infoClass The {@link java.lang.Class} which is generating the message
     * @param message The {@link java.lang.String} representing the message to log in.
     * @see java.lang.Class
     * @see java.lang.String
     * @since 1.0
     */

    @Override
    public void logInfo(Class infoClass, String message) {
     org.apache.log4j.Logger logger = log(infoClass, Level.INFO);
     logger.info(message);   
    }
    
    /**
     * 
     * @param infoClass The {@link java.lang.Class} which is generating the message
     * @param message  The {@link java.lang.String} representing the message to log in.
     * @param throwable The exception generated.
     * @see java.lang.Class
     * @see java.lang.String
     * @see java.lang.Throwable
     * @since 1.0
     */

    @Override
    public void logInfo(Class infoClass, String message, Throwable throwable) {
     org.apache.log4j.Logger logger = log(infoClass, Level.INFO);
     logger.info(message,throwable);    
    }
    
    /**
     * 
     * @param message The {@link java.lang.String} representing debug message to log in.
     * @see java.lang.String
     * @since 1.0
     */

    @Override
    public void logDebug(String message) {
     org.apache.log4j.Logger logger = log(null, Level.DEBUG);
     logger.debug(message);    
    }
    
    /**
     *  
     * @param debugClass The {@link java.lang.Class} generating the debug message 
     * @param message The {@link java.lang.String} representing the debug message to log in.
     * @see java.lang.String
     * @see java.lang.Class
     * @since 1.0
     */

    @Override
    public void logDebug(Class debugClass, String message) {
        org.apache.log4j.Logger logger = log(debugClass, Level.DEBUG);
        logger.debug(message);
    }

    @Override
    public void logDebug(Class debugClass, String message, Throwable throwable) {
        org.apache.log4j.Logger logger = log(debugClass, Level.DEBUG);
        logger.debug(message,throwable);
    }

    @Override
    public void logError(String message) {
     org.apache.log4j.Logger logger = log(null, Level.ERROR);
        logger.error(message);   
    }

    @Override
    public void logError(Class errorClass, String message) {
        org.apache.log4j.Logger logger = log(errorClass, Level.ERROR);
        logger.debug(message);
    }

    @Override
    public void logError(Class errorClass, String message, Throwable throwable) {
      org.apache.log4j.Logger logger = log(errorClass, Level.ERROR);
        logger.debug(message,throwable);  
    }

    public boolean isRollingOn() 
    {
        return rollingOn;
    }

    public void setRollingOn(boolean rollingOn) {
        this.rollingOn = rollingOn;
    }

    public String getDatePattern() {
         if(datePattern == null)
            datePattern="dd-MM-yyyy";
        return datePattern;
    }

    public void setDatePattern(String datePattern) 
    {
        this.datePattern = datePattern;
    }
    
    
    
}
