package stashpullrequestbuilder.stashpullrequestbuilder.stash;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * If pull request is mergeable
 * https://developer.atlassian.com/static/rest/stash/3.9.2/stash-rest.html#idp2785024
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StashPullRequestMergableResponse {

    private static final String NEEDS_WORK_SUMMARY_MESSAGE = "Needs work";

    private Boolean canMerge;
    private Boolean conflicted;
    private ArrayList<StashPullRequestMergableVetoMessage> vetoes ;

    public Boolean getCanMerge() {
        return canMerge;
    }

    public void setCanMerge(Boolean canMerge) {
        this.canMerge = canMerge;
    }

    public Boolean getConflicted() {
        return conflicted;
    }

    public void setConflicted(Boolean conflicted) {
        this.conflicted = conflicted;
    }

    public ArrayList<StashPullRequestMergableVetoMessage> getVetoes() {
        return vetoes;
    }

    public void setVetoes(ArrayList<StashPullRequestMergableVetoMessage> vetoes) {
        this.vetoes = vetoes;
    }

    public Boolean isNeedsWork() {
        for (StashPullRequestMergableVetoMessage veto : getVetoes()) {
            if (veto.getSummaryMessage().equals(NEEDS_WORK_SUMMARY_MESSAGE)) {
                return true;
            }
        }
        return false;
    }
}
