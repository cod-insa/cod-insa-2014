from MaterialEntity import MaterialEntity

class MovingEntity(MaterialEntity):
    
    def __init__(self, id, coord, rotation, radar_range, inertia):
        MaterialEntity.__init(self, id, coord, rotation, radar_range)
        self.inertia = inertia