package stashpullrequestbuilder.stashpullrequestbuilder.stash;

import org.junit.Test;

/**
 * Created by nathan on 7/06/2015.
 */
public class StashApiClientTest {

    private static final String MERGABLE_STATUS = "{\"canMerge\":true,\"conflicted\":false,\"outcome\":\"CLEAN\",\"vetoes\":[]}";

    private static final String NOT_MERGABLE_STATUS = "{\"canMerge\":false,\"conflicted\":false,\"vetoes\":[{\"summaryMessage\":\"You may not merge after 6pm on a Friday.\",\"detailedMessage\":\"It is likely that your Blood Alcohol Content (BAC) exceeds the threshold for making sensible decisions regarding pull requests. Please try again on Monday.\"}]}";

    private static final String NEEDS_WORK_STATUS = "{\"canMerge\":false,\"conflicted\":true,\"outcome\":\"CONFLICTED\",\"vetoes\":[{\"summaryMessage\":\"Not all required builds are successful yet\",\"detailedMessage\":\"You cannot merge this pull request while it has failed builds.\"},{\"summaryMessage\":\"Requires approvals\",\"detailedMessage\":\"You need 3 more approvals before this pull request can be merged.\"},{\"summaryMessage\":\"Needs work\",\"detailedMessage\":\"PR marked as Needs Work from reviewer(s)\"}]}";

    @Test
    public void testParsePullRequestMergeStatusMergable() throws Exception {
        StashPullRequestMergableResponse resp = StashApiClient.parsePullRequestMergeStatus(MERGABLE_STATUS);
        assert (resp != null);
        assert (resp.getCanMerge());
        assert (!resp.getConflicted());
        assert (!resp.isNeedsWork());
        assert (resp.getVetoes().size() == 0);
    }

    @Test
    public void testParsePullRequestMergeStatusNotMergable() throws Exception {
        StashPullRequestMergableResponse resp = StashApiClient.parsePullRequestMergeStatus(NOT_MERGABLE_STATUS);
        assert (resp != null);
        assert (!resp.getCanMerge());
        assert (!resp.getConflicted());
        assert (!resp.isNeedsWork());
        assert (resp.getVetoes().size() == 1);
    }

    @Test
    public void testParsePullRequestMergeStatusThatNeedsWork() throws Exception {
        StashPullRequestMergableResponse resp = StashApiClient.parsePullRequestMergeStatus(NEEDS_WORK_STATUS);
        assert (resp != null);
        assert (!resp.getCanMerge());
        assert (resp.getConflicted());
        assert (resp.isNeedsWork());
        assert (resp.getVetoes().size() == 3);
    }
}