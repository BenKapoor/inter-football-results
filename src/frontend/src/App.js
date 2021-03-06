import './App.scss';

import { Route, BrowserRouter as Router, Switch } from 'react-router-dom';

import { MatchPage } from './pages/MatchPage';
import { TeamPage } from './pages/TeamPage';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch>
          <Route path="/teams/:teamName/matches/:year">
            <MatchPage />
          </Route>
          <Route path="/teams/:teamName">
            <TeamPage />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
