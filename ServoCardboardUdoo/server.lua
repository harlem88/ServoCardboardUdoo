-- Import turbo,
local turbo = require("turbo")

local wserial = io.open("/dev/ttyMCC", "w")

local DirectionHandler = class("DirectionHandler", turbo.web.RequestHandler)

function DirectionHandler:post()
    local data = self:get_json(true)
    print("direction: " .. data.direction)
    if data.direction == "left" then
        print("Received LEFT command...")
        wserial:write("2")
    elseif data.direction == "right" then
        print("Received RIGHT command...")
	wserial:write("1")
    elseif data.direction == "center" then
        print("Received RESET command...")
	wserial:write("0")
    end
    wserial:flush()
end

-- Create an Application object and bind our HelloWorldHandler to the route '/hello'.
local app = turbo.web.Application:new({
    {"/api/direction", DirectionHandler},
})

-- Set the server to listen on port 8888 and start the ioloop.
app:listen(8888)
turbo.ioloop.instance():start()
