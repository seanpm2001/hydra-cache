package org.hydracache.console

import org.hydracache.console.util.ClosureTimerTask
import griffon.util.GriffonApplicationHelper

class NodeDetailPaneController {
    static final long UPDATER_INIT_DELAY = 5000
    static final long UPDATER_INTERVAL = 10000

    // these will be injected by Griffon
    def model
    def view

    def hydraSpaceService

    def nodeUpdater = new Timer()

    void mvcGroupInit(Map args) {
        model.storageInfo = args.storageInfo
        model.server = args.server

        log.debug "Creating node detail pane for ${model.server}"

        nodeUpdater.schedule(new ClosureTimerTask(closure: {
            log.debug "Updating node[${model.server}] detail..."

            def info = hydraSpaceService.queryServerDetails(model.server)

            model.storageInfo = info

            updateNodeDetail()

            log.debug "Node[${model.server}] detail updated"
        }), UPDATER_INIT_DELAY, UPDATER_INTERVAL)

        updateNodeDetail()
    }

    def updateNodeDetail() {
        doLater {
            model.updateDetails()
        }
    }

    def onHydraSpaceDisConnected = {
        log.debug "Event [HydraSpaceDisConnected] received ..."

        log.debug "Destroying MVC group [${model.server}]"

        nodeUpdater.cancel()
        
        GriffonApplicationHelper.destroyMVCGroup(app, "${model.server}")

        log.debug "Event [HydraSpaceDisConnected] processed"
    }
}