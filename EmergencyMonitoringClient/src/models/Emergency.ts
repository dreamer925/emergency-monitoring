export enum EmergencyType {
    Blizzards, Chemical_spills, Dam_failure,
    Droughts, Earthquake,
    Extreme_heat_waves, Fire,
    Floods, Hurricanes,
    Pandemics, Terrorist_attacks,
    Thunder_storms, Tornadoes,
    Other
  }
  
  export class Emergency {
    id: number;
    name: string;
    country: string;
    city: string;
    location: string;
    date: Date;
    type: EmergencyType;
  
    constructor(
      id: number,
      name: string,
      country: string,
      city: string,
      date: Date,
      type: EmergencyType
    ) {
      this.id = id;
      this.name = name;
      this.country = country;
      this.city = city;
      this.location = `${country}, ${city}`;
      this.date = date;
      this.type = type;
    }
  }  