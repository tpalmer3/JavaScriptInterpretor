function file_to_string(name) do
	str = ""
	local f = assert(io.open(name, "rb"))
	for l in ipairs(f:lines()) do
		str = str..l
	end
	return str
end end
