cities = {}
cities[1] = { :name => 'Lyon',                  :prog => 'ruby',    :win => 0 }
cities[2] = { :name => 'Rennes',                :prog => 'ruby',    :win => 0 }
cities[3] = { :name => 'Strasbourg',            :prog => 'ruby',    :win => 0 }
cities[4] = { :name => 'Toulouse',              :prog => 'ruby',    :win => 0 }
cities[5] = { :name => 'Rouen',                 :prog => 'ruby',    :win => 0 }
cities[6] = { :name => 'Centre Val de Loire',   :prog => 'ruby',    :win => 0 }

maps = ['france']

times = [100, 1000, 3600]

battles = []
[1,2,3,4,5,6].combination(2).to_a.each do |o|
    tmp = []
    times.each do |t|
        maps.each do |m|
            tmp << { :op => o, :time => t, :map => m }
            tmp << { :op => o.reverse, :time => t, :map => m }
        end
    end
    battles << tmp
end

battles.each do |bs|
    bs.each do |b|
        ip = 9090
        players = ""
        b[:op].each do |op|
            players << cities[op][:name] << ' ' << ip.to_s << ' '
            ip = ip + 1
        end
        puts "java -jar server.jar #{b[:map]} #{b[:time].to_s} #{players}"
    end
end
