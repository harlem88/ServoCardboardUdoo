-- Import turbo,
local turbo = require("turbo")

-- Create a new requesthandler with a method get() for HTTP GET.
local commander = {}
commander.left = "./commander.sh /dev/ttyMCC 1"
commander.right = "./commander.sh /dev/ttyMCC 2"
commander.center = "./commander.sh /dev/ttyMCC 0"

local DirectionHandler = class("DirectionHandler", turbo.web.RequestHandler)

function DirectionHandler:post()
    local data = self:get_json(true)
    print("direction: " .. data.direction)

    if data.direction == "left" then
        print("Received LEFT command...")
        io.popen(commander.left)
    elseif data.direction == "right" then
        print("Received RIGHT command...")
        io.popen(commander.right)
    elseif data.direction == "center" then
        print("Received CENTER command...")
        io.popen(commander.center)	
    end
end

-- Create an Application object and bind our HelloWorldHandler to the route '/hello'.
local app = turbo.web.Application:new({
    {"/api/direction", DirectionHandler},
})

-- Set the server to listen on port 8888 and start the ioloop.
app:listen(8888)
turbo.ioloop.instance():start()
