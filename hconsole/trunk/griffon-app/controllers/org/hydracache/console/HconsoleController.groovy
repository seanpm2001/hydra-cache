package org.hydracache.console
class HconsoleController {
    def model
    def view
    def hydraSpaceService

    void mvcGroupInit(Map args) {
        createMVCGroup("NavigationPane", [navigationPane: view.navigationPane])
        createMVCGroup("AddressBar", [addressBar: view.addressBar])
        createMVCGroup("PlayGround", [playgroundPane: view.playgroundPane])
    }

    def quit = {evt = null ->
        app.shutdown()
    }

    def onHydraSpaceConnected = {nodes, storageInfo ->
        log.debug "Event [HydraSpaceConnected] received ..."
        
        doLater {
            log.debug "Creating SpaceDashboard ..."
            
            createMVCGroup('SpaceDashboard',
                    'SpaceDashboard',
                    [tabGroup: view.tabGroup])

            app.controllers['SpaceDashboard'].update(nodes, storageInfo)
        }
    }

    def onHydraSpaceDisConnected = {
        log.debug "Event [HydraSpaceDisConnected] received ..."

        doLater {
            view.tabGroup.removeAll()
        }
    }

}