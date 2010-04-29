/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import jpa.entities.Consultant;

/**
 *
 * @author nb
 */
public class AuthenticationPhaseListener implements PhaseListener {

    /**
    * <p>The outcome to trigger navigation to the login page.</p>
    */
    private static final String LOGIN_OUTCOME = "login";

    /**
    * <p>The outcome to trigger navigation to the welcome page.</p>
    */
    private static final String WELCOME_OUTCOME = "welcome";

    /**
    * <p>The session attribute key used to store an authenticated consultant.</p>
    */
    public static final String AUTHENTICATED_CONSULTANT_KEY = "jsfcrud.timecard.consultant";

    /**
    * <p>Invoke <code>verifyView</code> if the current phase is apply request values or render response.</p>
    */
    public void beforePhase(PhaseEvent event) {
        PhaseId phase = event.getPhaseId();
        if (PhaseId.APPLY_REQUEST_VALUES.equals(phase) || PhaseId.RENDER_RESPONSE.equals(phase)) {
            verifyView(event);
        }
    }

    /**
    * <p>No-op.</p>
    */
    public void afterPhase(PhaseEvent event) {
    }

    /**
    * <p>If the requested view is forbidden, change the requested
    * view to the welcome view; then if the user
    * is not logged in and is requesting a secure view,
    * direct the user to the login page; otherwise, direct the user
    * to the requested view.</p>
    */

    private void verifyView(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        String viewId = context.getViewRoot().getViewId();
        String forwardToOutcome = null;
        if (isForbiddenView(viewId)) {
            JsfUtil.addErrorMessage("You do not have permission to view the requested page.");
            //change requested view to the welcome view
            //and assume we will forward to that view
            viewId = "/welcomeJSF.jsp";
            forwardToOutcome = WELCOME_OUTCOME;
        }
        if (getLoggedInConsultant() == null) {
            if (isSecureView(viewId)) {
                forwardToOutcome = LOGIN_OUTCOME;
            }
        }
        if (forwardToOutcome != null) {
            context.getApplication(). getNavigationHandler().handleNavigation(context, null, forwardToOutcome);
        }
    }

    /**
    * <p>Specify <code>PhaseId.ANY_PHASE</code> and execute logic conditionally
    * in <code>beforePhase</code> if the phase is apply request values or
    * render response.</p>
    * @return <code>PhaseId.ANY_PHASE</code>.
    */
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /**
    * <p>Obtain the logged in consultant stored in session (or null).</p>
    * @return the logged in <code>Consultant</code> or <code>null</code> if
    * no such <code>Consultant</code> exists.
    */
    public static Consultant getLoggedInConsultant() {
        return (Consultant)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AuthenticationPhaseListener.AUTHENTICATED_CONSULTANT_KEY);
    }

    /**
    * <p>Determines if the requested view requires authentication.</p>
    * @param context the <code>FacesContext</code> for the current request.
    * @return <code>true</code> if the requested view requires authentication, otherwise <code>false</code>.
    */
    private boolean isSecureView(String viewId) {
        if (viewId == null) {
            return false;
        }
        if (isUnrestrictedView(viewId)) {
            return false;
        }
        return ("/welcomeJSF.jsp".equals(viewId) || viewId.startsWith("/billable/"));
    }

    /**
    * <p>Determines if the requested view is forbidden, namely,
    * any resource that is not considered unrestricted or secure.</p>
    * @param context the <code>FacesContext</code> for the current request.
    * @return <code>true</code> if the requested view is forbidden, otherwise <code>false</code>.
    */
    private boolean isForbiddenView(String viewId) {
        if (viewId == null) {
            return true;
        }
        //only unrestricted and secure views are permitted
        if (isUnrestrictedView(viewId) || isSecureView(viewId)) {
        return false;
        }
        return true;
    }

    /**
    * <p>Determine if the view supplied is unrestricted, that is, can be accessed
    * without authenticating. For this example, the login page, JavaScript resources,
    * CSS resources, and image resources are unrestricted.</p>
    * @param viewId The supplied view.
    * @return Whether the supplied view is unrestricted.
    */
    private boolean isUnrestrictedView(String viewId) {
        return "/login.jsp".equals(viewId) || "/jsfcrud.js".equals(viewId) || "/jsfcrud.css".equals(viewId) || "/busy.gif".equals(viewId);
    }

}
