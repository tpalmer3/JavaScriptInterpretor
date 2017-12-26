local redis = require "redis"

local file = require "file"

redis.commands["eval"] = redis.command("EVAL")
redis.commands["evalsha"] = redis.command("EVALSHA")
redis.commands["s_debug"] = redis.command("SCRIPT DEBUG")
redis.commands["s_exists"] = redis.command("SCRIPT EXISTS")
redis.commands["s_flush"] = redis.command("SCRIPT FLUSH")
redis.commands["s_kill"] = redis.command("SCRIPT KILL")
redis.commands["s_load"] = redis.command("SCRIPT LOAD")

redis.commands["geoadd"] = redis.command("GEOADD")
redis.commands["geodist"] = redis.command("GEODIST")
redis.commands["geohash"] = redis.command("GEOHASH")
redis.commands["geopos"] = redis.command("GEOPOS")
redis.commands["georadius"] = redis.command("GEORADIUS")
redis.commands["georadiusbymember"] = redis.command("GEORADIUSBYMEMBER")

--[[redis.commands["dist"] = "WITHDIST"
redis.commands["coord"] = "WITHCOORD"
redis.commands["hash"] = "WITHHASH"
redis.commands["asc"] = "ASC"
redis.commands["desc"] = "DESC"]]

-- local client = redis.connect("192.168.99.100", 6379)
local client = redis.connect("127.0.0.1", 6379)

function get_client() do
	return client
end end

local function get_geopos(...) do
	for cnt, list in ipairs(client:geopos(...)) do
			print("Latitude:",list[2])
			print("Longitude:",list[1])
	end
end end

--[[local function client.get_geopos(...) do
	return client:script(
end end]]

--[[local function client.get_in_radius(...) do			--Not Quite Working Yet
	for key, val in ipairs(client:georadius(...)) do
		print(val[1],":",val[2])
		end
	end
end]]

local function load_file(key, val) do
	client:s_load(file:file_to_string(val))
end end

return client