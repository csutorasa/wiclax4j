package com.github.csutorasa.wiclax.requesthandler;

import com.github.csutorasa.wiclax.request.RewindRequest;
import com.github.csutorasa.wiclax.response.WiclaxResponse;

/**
 * Rewind request to resend all acquisitions between the from and to times.
 */
public class RewindRequestHandler extends AbstractWiclaxRequestHandler<RewindRequest> {

    private final RewindHandler rewindTask;

    /**
     * Creates a new handler.
     *
     * @param rewindTask task to execute
     */
    public RewindRequestHandler(RewindHandler rewindTask) {
        super(RewindRequest.class);
        this.rewindTask = rewindTask;
    }

    @Override
    public WiclaxResponse handleRequest(RewindRequest request) {
        rewindTask.accept(request.getFrom(), request.getTo());
        return null;
    }
}
