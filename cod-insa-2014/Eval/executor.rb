cities = {}
cities[1] = { :name => 'Lyon', :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/CodINSALyon && ./run.sh localhost ' }
cities[2] = { :name => 'Rouen', :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/insaRouen && java -jar rouenIA.jar localhost ' }
cities[3] = { :name => 'Strasbourg', :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/strasbourg_vous_fait_perdre && java -jar ai.jar localhost ' }
cities[4] = { :name => 'Toulouse', :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/rouen && java -jar rouenIA.jar localhost ' } # TODO
cities[5] = { :name => 'Rennes', :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/rouen && java -jar rouenIA.jar localhost ' } # TODO
cities[6] = { :name => 'Centre Val de Loire',  :prog => 'cd /home/black/programmation/C++/codinsa2014/cod-insa-2014/AIs_Final/CDL && java -jar monIA.jar localhost ' } # Compile

mapfile = 'cuba'

ROOT = '/home/black/programmation/C++/codinsa2014/public-2014'
TIME = 300

# battles = []
# [1,2,3,4,5,6].combination(2).to_a.each do |o|
#     tmp = []
#     maps.each do |m|
#         tmp << { :op => o, :map => m }
#         tmp << { :op => o.reverse, :map => m }
#     end
#     battles << tmp
# end

# battles.each do |bs|
    # bs.each do |b|
    ip = 9090
    players = ""
    ARGV.each do |c|
        players << '"' << cities[c.to_i][:name] << '" ' << ip.to_s << ' '
        ip = ip + 1
        ip = 9092 if ip == 9091
    end
    puts "cd #{ROOT} && java -jar server.jar #{mapfile} #{TIME} #{players} &"
    puts "sleep 4"

    ip = 9090
    ARGV.each do |c|
        tip = ip.to_s
        ip = ip + 1
        ip = 9092 if ip == 9091
        puts "#{cities[c.to_i][:prog]} #{tip} &"
    end
    puts "sleep 1000"
# end
