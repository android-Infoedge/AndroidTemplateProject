package com.android.base.gcm;


import com.android.base.gcm.model.NotificationCommand;

/**
 * This is interface which will be implemented by some concrete class to handle
 * GCM message in specific and logically separate way.
 *
 * <p>Idea of creating this interface is as follows:-<br>
 * As it may be the case that we need to handle GCM notification sent by GCM server
 * , triggered by application server in different ways eg. some times we need to show
 * local notification,some time we may need to just do some processing in background at the
 * signal of application server.So it is logical to write these different functionality  in
 * respective concrete classes, so that it is easy to manage code at one place.</p>
 *
 * <p>Now if GCM message needs to be handled in some specific way , then new class must implement
 * this interface.The message receiving entity{@link GcmHandlerService} does not need to
 * know to which direction it needs to send.</p>
 *
 * @see LocalNotificationExecutor
 * @author Gagandeep Singh
 * @version 1.0
 * @since 1.0
 */
public interface GcmCommandExecutor {

    /**
     * Writes concrete functionality to handle GCM message represented by {@link NotificationCommand}
     * in some specific way.
     *
     * @param  command  GCM message sent by application server via GCM server
     * @see         LocalNotificationExecutor#execute(NotificationCommand)
     */
    public void execute(NotificationCommand command, String responseString);
}
