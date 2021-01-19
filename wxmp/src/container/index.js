import { createContainer } from "unstated-next";
import useUser from "../hooks/user";

function useApp(initValue){     
    const user = useUser()
    return {user}
}

let AppContainer = createContainer(useApp);

export default AppContainer;